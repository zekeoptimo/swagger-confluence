package com.zekeoptimo.swagger2confluence.model.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by zekeo on 4/20/2017.
 */
public class Parameter extends BaseAdditionalAttributes {
    private String name;
    @JsonProperty(value="in")
    private String inValue;
    private String description;
    private Boolean required;

    // Body Parameter
    private Schema schema;

    // Non-Body Parameter
    private String type;
    private String format;
    private Boolean allowEmptyValue;
    private Item items;
    private String collectionFormat;
    @JsonProperty(value="default")
    private Object defaultValue;
    @JsonProperty(value="enum")
    private List<Object> enums;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInValue() {
        return inValue;
    }

    public void setInValue(String inValue) {
        this.inValue = inValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Boolean getAllowEmptyValue() {
        return allowEmptyValue;
    }

    public void setAllowEmptyValue(Boolean allowEmptyValue) {
        this.allowEmptyValue = allowEmptyValue;
    }

    public Item getItems() {
        return items;
    }

    public void setItems(Item items) {
        this.items = items;
    }

    public String getCollectionFormat() {
        return collectionFormat;
    }

    public void setCollectionFormat(String collectionFormat) {
        this.collectionFormat = collectionFormat;
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
}
