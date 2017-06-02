package com.zekeoptimo.swagger2confluence.model.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zekeo on 4/20/2017.
 */
public class Reference extends BaseAdditionalAttributes  {
    @JsonProperty(value = "$ref")
    private String ref;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
