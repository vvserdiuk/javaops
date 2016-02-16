package ru.javaops.service;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.javaops.JavaOPsApplication;
import ru.javaops.model.User;
import ru.javaops.util.Util;

/**
 * GKislin
 * 16.02.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JavaOPsApplication.class)
@WebAppConfiguration
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testGetContent() throws Exception {
        String content = mailService.getContent("masterjava_register", ImmutableMap.of("user",
                new User("email@ya.ru", "Name Surname", "location", "info source"), "template", "topjava_register", "result", "OK"));
        System.out.println("\n+++++");
        System.out.println(Util.getTitle(content));
        System.out.println("+++++");
        System.out.println(content);
    }
}