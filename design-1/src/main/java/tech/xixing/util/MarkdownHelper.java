package tech.xixing.util;

import java.util.ArrayList;
import java.util.List;

import static tech.xixing.util.MarkdownDoc.CARRIAGE_RETURN;

/**
 * @author liuzhifei
 * @since 1.0
 */
public final class MarkdownHelper {

    private MarkdownHelper() {
    }

    public static MarkdownDoc newDoc() {
        return new MarkdownDoc();
    }

    public static String buildLink(String name, String url) {
        return "[" + name + "](" + url + ")";
    }

    public static String buildImage(String imageUrl) {
        return "![](" + imageUrl + ")";
    }

    public static String buildItalicContent(String content) {
        return "*" + content + "*";
    }

    public static String buildItalicContent(String pattern, Object... args) {
        return buildItalicContent(String.format(pattern, args));
    }

    public static String buildBoldContent(String content) {
        return "**" + content + "**";
    }

    public static String buildBoldContent(String pattern, Object... args) {
        return buildBoldContent(String.format(pattern, args));
    }

    public static String buildBoldItalicContent(String content) {
        return "***" + content + "***";
    }

    public static MarkdownAppender buildBoldItalicContentAppender() {
        return new MarkdownAppender("***", "***");
    }


    public static String buildBoldItalicContent(String pattern, Object... args) {
        return buildBoldItalicContent(String.format(pattern, args));
    }

    public static MarkdownAppender buildQuoteContentAppender() {
        return new MarkdownAppender("> ", CARRIAGE_RETURN);
    }

    public static MarkdownAppender buildAppender(String startSymbol, String endSymbol) {
        return new MarkdownAppender(startSymbol, endSymbol);
    }

    public static MarkdownAppender buildErrorColorContentAppender() {
        return new MarkdownAppender("<font color=#ff4d4f>", "</font>");
    }

    public static MarkdownAppender buildRecoveryColorContentAppender() {
        return new MarkdownAppender("<font color=#11d929>", "</font>");
    }

    public static MarkdownAppender buildCommonTextAppender() {
        return new MarkdownAppender("", "\n\n");
    }


    public static String buildDivider() {
        return "***" + CARRIAGE_RETURN;
    }

    public static class MarkdownAppender {

        private StringBuilder markdownBuilder = new StringBuilder();

        private List<MarkdownAppender> addedAppender = new ArrayList<>();

        private String startSymbol;

        private String endSymbol;

        public MarkdownAppender(String startSymbol, String endSymbol) {
            this.startSymbol = startSymbol;
            this.endSymbol = endSymbol;
            markdownBuilder.append(startSymbol);
        }

        public MarkdownAppender append(String content) {
            markdownBuilder.append(content);
            return this;
        }

        public MarkdownAppender append(MarkdownAppender appender) {
            addedAppender.add(appender);
            // 占位
            markdownBuilder.append("%s");
            return this;
        }

        public String end() {
            String formatedAppender = markdownBuilder.append(endSymbol).toString();
            if (addedAppender.size() == 0) {
                return formatedAppender;
            }
            List<String> formatValues = new ArrayList<>();
            for (MarkdownAppender markdownAppender : addedAppender) {
                formatValues.add(markdownAppender.end());
            }
            return String.format(formatedAppender, formatValues.toArray());
        }

    }
}
