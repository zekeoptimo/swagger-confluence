package com.zekeoptimo.swagger2confluence.model.swagger;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for "additional attributes" so that our JSON serialize and deserialize have a place to put elements
 * that don't fit into the simplified models that have been defined.
 *
 * @author zekeo
 */
public class BaseAdditionalAttributes {
    private Map<String, Object> additionalAttributes = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }

    @JsonIgnore
    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    @JsonAnySetter
    public void addAdditionalAttribute(String key, Object value) {
        additionalAttributes.put(key, value);
    }
}
