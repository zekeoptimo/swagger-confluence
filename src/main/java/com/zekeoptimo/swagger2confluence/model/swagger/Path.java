package com.zekeoptimo.swagger2confluence.model.swagger;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zekeo on 4/20/2017.
 */
public class Path {
    private Map<String, Operation> operations = new HashMap<>();

    private List<Parameter> parameters;

    @JsonAnyGetter
    public Map<String, Operation> getOperations() {
        return operations;
    }

    @JsonIgnore
    public void setOperations(Map<String, Operation> operations) {
        this.operations = operations;
    }

    @JsonAnySetter
    public void add(String key, Operation value) {
        this.operations.put(key, value);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}