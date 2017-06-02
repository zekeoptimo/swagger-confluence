package com.zekeoptimo.swagger2confluence.model.swagger;

import java.util.Map;

/**
 * Created by zekeo on 4/20/2017.
 */
public class Response extends BaseAdditionalAttributes {
    private String description;
    private Map<String, Object> schema;
    private Map<String, Header> headers;
    private Map<String, Map<String, Object>> examples;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getSchema() {
        return schema;
    }

    public void setSchema(Map<String, Object> schema) {
        this.schema = schema;
    }

    public Map<String, Header> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Header> headers) {
        this.headers = headers;
    }

    public Map<String, Map<String, Object>> getExamples() {
        return examples;
    }

    public void setExamples(Map<String, Map<String, Object>> examples) {
        this.examples = examples;
    }
}
