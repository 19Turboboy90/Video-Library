package ru.zharinov.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {
    private static final String JSP_FORMAT = "/WEB-INF/jsp/%s.jsp";

    public static String prefixPath(String jspName) {
        return String.format(JSP_FORMAT, jspName);
    }
}
