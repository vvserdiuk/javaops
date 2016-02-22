package ru.javaops.web;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.javaops.util.MarkdownUtil;

/**
 * Created by vvserdiuk on 22.02.2016.
 */
@Controller
public class MarkdownController {

    @RequestMapping(value = "/lesson01")
    ModelAndView lesson01() {
        String html =  new MarkdownUtil().toHtml(new FileSystemResource("resources/lesson01.md"));
        return new ModelAndView("forMarkdown").addObject("text", html);
    }
}
