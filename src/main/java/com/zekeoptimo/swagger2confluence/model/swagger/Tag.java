package com.zekeoptimo.swagger2confluence.model.swagger;

/**
 * Created by zekeo on 4/20/2017.
 */
public class Tag extends BaseAdditionalAttributes  {
    private String name;
    private String description;
    private ExternalDocumentation externalDocs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExternalDocumentation getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }
}
