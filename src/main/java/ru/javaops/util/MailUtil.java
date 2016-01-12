package ru.javaops.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GKislin
 * 22.08.2015.
 */
public class MailUtil {
    private static Pattern MAIL_TITLE = Pattern.compile("<title>(.+)</title>", Pattern.MULTILINE);

    public static String getTitle(String template) {
        Matcher m = MAIL_TITLE.matcher(template);
        return m.find() ? m.group(1) : null;
    }
}
