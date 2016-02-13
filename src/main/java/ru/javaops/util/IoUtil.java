package ru.javaops.util;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author grigory.kislin
 *         Date: 04-Aug-15.
 */
public class IoUtil {

    public static String toString(Resource resource) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return FileCopyUtils.copyToString(br);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
