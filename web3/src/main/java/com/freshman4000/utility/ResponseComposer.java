package com.freshman4000.utility;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ResponseComposer {
    public static void formResponse(HttpServletResponse resp, int status, Map<String, Object> pageVars, PageGenerator pg, String message) throws IOException {
        pageVars.clear();
        pageVars.put("message", message);
        resp.setStatus(status);
        resp.getWriter().println(pg.getPage("resultPage.html", pageVars));
    }
}
