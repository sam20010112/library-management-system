package com.library.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    
    private static final Pattern[] XSS_PATTERNS = {
        Pattern.compile("<script.*?>.*?</script>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<script.*?>.*?</script>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<iframe.*?>.*?</iframe>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<object.*?>.*?</object>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<embed.*?>.*?</embed>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<link.*?>.*?</link>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<meta.*?>.*?</meta>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<style.*?>.*?</style>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onload", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onerror", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onclick", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onmouseover", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onfocus", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onblur", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onchange", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onsubmit", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onreset", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onselect", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onkeydown", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onkeyup", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onkeypress", Pattern.CASE_INSENSITIVE)
    };
    
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return cleanXss(value);
    }
    
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                values[i] = cleanXss(values[i]);
            }
        }
        return values;
    }
    
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        // Don't filter HTTP headers to avoid breaking functionality
        // Only filter request parameters for XSS protection
        return value;
    }
    
    private String cleanXss(String value) {
        if (value == null) {
            return null;
        }
        
        // Remove all XSS patterns
        for (Pattern pattern : XSS_PATTERNS) {
            value = pattern.matcher(value).replaceAll("");
        }
        
        // Additional cleaning
        value = value.replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("\"", "&quot;")
                    .replaceAll("'", "&#x27;")
                    .replaceAll("/", "&#x2F;");
        
        return value;
    }
}


