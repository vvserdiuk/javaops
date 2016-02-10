package ru.javaops.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * GKislin
 * 11.02.2016
 */
public class PasswordUtilTest {

    @Test
    public void testPassword() throws Exception {
        String rawPassword = "qwerty123456";
        String encoded = PasswordUtil.encode(rawPassword);
        System.out.println(encoded);
        assertTrue(PasswordUtil.isEncoded(encoded));
        assertTrue(PasswordUtil.isMatch(rawPassword, encoded));
    }
}