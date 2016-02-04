package ru.javaops.web.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javaops.JavaOPsApplication;

/**
 * GKislin
 * 05.02.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JavaOPsApplication.class)
public class ResultTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetResult() throws Exception {
        final Result<String> r = Result.ok("OK");
        String str = objectMapper.writeValueAsString(r);
        System.out.println(str);
        Assert.assertEquals(r, objectMapper.readerFor(Result.class).readValue(str));
    }
}