package tech.xixing.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class MarkdownDoc {
    public static final char SPACE = ' ';
    public static final String CARRIAGE_RETURN = "\n\n";
    private final StringBuilder doc = new StringBuilder();
    private int titleLevel = 1;
    private int disorderListLevel = 1;
    private int orderlyListLevel = 1;

    protected MarkdownDoc() {

    }

    private String repeat(char ch, int num) {
        return StringUtils.repeat(ch, num);
    }

    @Override
    public String toString() {
        return doc.toString();
    }

    public boolean isEmpty() {
        return doc.length() == 0;
    }

    public MarkdownDoc newLine() {
        disorderListLevel = 1;
        orderlyListLevel = 1;
        doc.append(CARRIAGE_RETURN);
        return this;
    }

    public MarkdownDoc nextContentLine() {
        doc.append(CARRIAGE_RETURN);
        return newLine();
    }

    public MarkdownDoc addTitle(int level, String title) {
        titleLevel = level;
        disorderListLevel = 1;
        String prefix = repeat('#', level);
        doc.append(prefix).append(SPACE).append(title).append(CARRIAGE_RETURN);
        return this;
    }

    public MarkdownDoc addTitle(String title) {
        return addTitle(titleLevel, title);
    }

    public MarkdownDoc resetTitleLevel(int level) {
        titleLevel = level;
        return this;
    }

    public MarkdownDoc resetTitleLevelToRoot() {
        return resetTitleLevel(1);
    }

    public MarkdownDoc resetTitleRelativeLevel(int relativeLevel) {
        return resetTitleLevel(disorderListLevel + relativeLevel);
    }

    public MarkdownDoc addDisorderList(int level, String content) {
        disorderListLevel = level;
        String prefix = repeat(SPACE, 2 * (disorderListLevel - 1));
        doc.append(prefix).append("-").append(SPACE).append(content).append(CARRIAGE_RETURN);
        return this;
    }

    public MarkdownDoc addChildDisorderList(String content) {
        return addDisorderList(++disorderListLevel, content);
    }

    public MarkdownDoc addDisorderList(String content) {
        return addDisorderList(disorderListLevel, content);
    }

    public MarkdownDoc addUncleDisorderList(String content) {
        return addDisorderList(--disorderListLevel, content);
    }

    public MarkdownDoc resetDisorderListLevel(int level) {
        disorderListLevel = level;
        return this;
    }

    public MarkdownDoc resetDisorderListLevelToRoot() {
        return resetDisorderListLevel(1);
    }

    public MarkdownDoc resetDisorderListRelativeLevel(int relativeLevel) {
        return resetDisorderListLevel(disorderListLevel + relativeLevel);
    }

    public MarkdownDoc addOrderlyList(int level, String content) {
        orderlyListLevel = level;
        String prefix = repeat(SPACE, 2 * (orderlyListLevel - 1));
        doc.append(prefix).append(orderlyListLevel).append(".").append(SPACE).append(content).append(CARRIAGE_RETURN);
        return this;
    }

    public MarkdownDoc addChildOrderlyList(String content) {
        return addOrderlyList(++disorderListLevel, content);
    }

    public MarkdownDoc addOrderlyList(String content) {
        return addOrderlyList(disorderListLevel, content);
    }

    public MarkdownDoc addUncleOrderlyList(String content) {
        return addOrderlyList(--disorderListLevel, content);
    }

    public MarkdownDoc resetOrderlyListLevel(int level) {
        orderlyListLevel = level;
        return this;
    }

    public MarkdownDoc resetOrderlyListLevelToRoot() {
        return resetOrderlyListLevel(1);
    }

    public MarkdownDoc resetOrderlyListRelativeLevel(int relativeLevel) {
        return resetOrderlyListLevel(orderlyListLevel + relativeLevel);
    }

    public MarkdownDoc addImage(String pictureUrl) {
        disorderListLevel = 1;
        doc.append(MarkdownHelper.buildImage(pictureUrl)).append(CARRIAGE_RETURN);
        return this;
    }

    public MarkdownDoc appendSpacing() {
        doc.append(SPACE);
        return this;
    }

    public MarkdownDoc appendContent(String content) {
        doc.append(content);
        return this;
    }

    public MarkdownDoc appendLinkContent(String name, String url) {
        return appendContent(MarkdownHelper.buildLink(name, url));
    }

    public MarkdownDoc appendBoldContent(String content) {
        return appendContent(MarkdownHelper.buildBoldContent(content));
    }

    public MarkdownDoc appendItalicContent(String content) {
        return appendContent(MarkdownHelper.buildItalicContent(content));
    }

    public MarkdownDoc appendBoldItalicContent(String content) {
        return appendContent(MarkdownHelper.buildBoldItalicContent(content));
    }

    public MarkdownDoc addQuoteContent(String content) {
        doc.append("> ").append(content).append(CARRIAGE_RETURN);
        return this;
    }
}