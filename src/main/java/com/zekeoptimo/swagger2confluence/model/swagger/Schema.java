package com.zekeoptimo.swagger2confluence.model.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by zekeo on 4/20/2017.
 */
public class Schema extends BaseAdditionalAttributes {
    @JsonProperty(value = "$ref")
    private String ref;
    private String type;
    private Map<String, String> items;
    private String format;
    private String title;
    private String description;
    private List<String> required;
    @JsonProperty(value = "default")
    private Object defaultValue;
    @JsonProperty(value = "enum")
    private List<Object> enums;
    @JsonProperty(value = "properties")
    private Map<String, Schema> propertiesValues;
    private Float multipleOf;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getItems() {
        return items;
    }

    public void setItems(Map<String, String> items) {
        this.items = items;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<Object> getEnums() {
        return enums;
    }

    public void setEnums(List<Object> enums) {
        this.enums = enums;
    }

    public Map<String, Schema> getPropertiesValues() {
        return propertiesValues;
    }

    public void setPropertiesValues(Map<String, Schema> propertiesValues) {
        this.propertiesValues = propertiesValues;
    }

    public Float getMultipleOf() {
        return multipleOf;
    }

    public void setMultipleOf(Float multipleOf) {
        this.multipleOf = multipleOf;
    }
}
