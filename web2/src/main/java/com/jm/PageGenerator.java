package com.jm;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PageGenerator {
    private static final String HTML_DIR = "src/resources/templates";
    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    public static PageGenerator getInstance() {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + File.separator + filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

    private PageGenerator() {
        cfg = new Configuration();
    }
    public Map<String, Object> createPageVariables(HttpServletRequest req) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("sessionId", req.getSession().getId());
        pageVariables.put("password", req.getParameter("password"));
        pageVariables.put("email", req.getParameter("email"));
        return pageVariables;
    }
}
