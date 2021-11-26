package com.neukrang.jybot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class UsageCounter {

    private Map<String, Long> counter;

    @PostConstruct
    public void load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream("usage.ser"))){
            counter = (Map<String, Long>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("커맨드 호출 횟수 파일을 읽는데 실패했습니다.");
            counter = new HashMap<>();
        }
    }

    @PreDestroy
    public void save() {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream("usage.ser", false))) {
            oos.writeObject(counter);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("커맨드 호출 횟수 파일을 저장하는데 실패했습니다.");
        }
    }

    @After("within(com.neukrang.jybot.command.skeleton.ICommand+) && " +
            "execution(* handle(..))")
    private void count(JoinPoint jp) {
        String commandName = getCommandName(jp.getSignature().getDeclaringTypeName());
        Long count = counter.getOrDefault(commandName, 0L);

        counter.put(commandName, ++count);
    }

    private String getCommandName(String name) {
        int lastDotIdx = name.lastIndexOf('.');
        return name.substring(lastDotIdx + 1);
    }
}
