package com.zekeoptimo.swagger2confluence.model.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by zekeo on 4/20/2017.
 */
public class Item extends BaseAdditionalAttributes {
    private String type;
    private String format;
    private Item items;
    private String collectionFormat;
    @JsonProperty(value = "default")
    private Object defaultValue;
    @JsonProperty(value = "enum")
    private List<Object> enums;

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
