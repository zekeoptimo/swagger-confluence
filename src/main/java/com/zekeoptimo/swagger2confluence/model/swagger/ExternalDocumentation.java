package com.zekeoptimo.swagger2confluence.model.swagger;

/**
 * Created by zekeo on 4/20/2017.
 */
public class ExternalDocumentation extends BaseAdditionalAttributes  {
    private String description;
    private String url;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
