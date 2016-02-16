package ru.javaops;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * GKislin
 * 16.02.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JavaOPsApplication.class)
@WebAppConfiguration
public abstract class ApplicationAbstractTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

}
