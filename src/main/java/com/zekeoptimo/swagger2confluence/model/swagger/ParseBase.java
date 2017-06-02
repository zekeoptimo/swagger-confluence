package com.zekeoptimo.swagger2confluence.model.swagger;

/**
 * Created by zekeo on 4/20/2017.
 */
public class ParseBase extends BaseAdditionalAttributes  {
    private String parseHeader;
    private String parseFooter;

    public String getParseHeader() {
        return parseHeader;
    }

    public void setParseHeader(String parseHeader) {
        this.parseHeader = parseHeader;
    }

    public String getParseFooter() {
        return parseFooter;
    }

    public void setParseFooter(String parseFooter) {
        this.parseFooter = parseFooter;
    }
}
