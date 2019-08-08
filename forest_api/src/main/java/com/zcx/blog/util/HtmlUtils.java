package com.zcx.blog.util;

public class HtmlUtils {
    public static String cleanHtmlTag(String content) {
        return content.replaceAll("(<[^<]*?>)|(<[\\s]*?/[^<]*?>)|(<[^<]*?/[\\s]*?>)", "");
    }
}
