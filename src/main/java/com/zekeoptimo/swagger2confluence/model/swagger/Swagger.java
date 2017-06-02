package com.zekeoptimo.swagger2confluence.model.swagger;

import java.util.List;
import java.util.Map;

/**
 * Created by zekeo on 4/20/2017.
 */
public class Swagger extends ParseBase {
    private String swagger;
    private Info info;
    private String host;
    private String basePath;
    private List<String> schemes;
    private List<String> consumes;
    private List<String> produces;
    private Map<String, Path> paths;
    private Map<String, Schema> definitions;
    private Map<String, Parameter> parameters;
    private Map<String, Response> responses;
    private Map<String, SecurityScheme> securityDefinitions;
    private Map<String, List<String>> security;
    private List<Tag> tags;
    private ExternalDocumentation externalDocs;

    public String getSwagger() {
        return swagger;
    }

    public void setSwagger(String swagger) {
        this.swagger = swagger;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public List<String> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<String> schemes) {
        this.schemes = schemes;
    }

    public List<String> getConsumes() {
        return consumes;
    }

    public void setConsumes(List<String> consumes) {
        this.consumes = consumes;
    }

    public List<String> getProduces() {
        return produces;
    }

    public void setProduces(List<String> produces) {
        this.produces = produces;
    }

    public Map<String, Path> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, Path> paths) {
        this.paths = paths;
    }

    public Map<String, Schema> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Map<String, Schema> definitions) {
        this.definitions = definitions;
    }

    public Map<String, Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Parameter> parameters) {
        this.parameters = parameters;
    }

    public Map<String, Response> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, Response> responses) {
        this.responses = responses;
    }

    public Map<String, SecurityScheme> getSecurityDefinitions() {
        return securityDefinitions;
    }

    public void setSecurityDefinitions(Map<String, SecurityScheme> securityDefinitions) {
        this.securityDefinitions = securityDefinitions;
    }

    public Map<String, List<String>> getSecurity() {
        return security;
    }

    public void setSecurity(Map<String, List<String>> security) {
        this.security = security;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public ExternalDocumentation getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }

    @Override
    public String toString() {
        return "Swagger{" +
                "swagger='" + swagger + '\'' +
                ", info=" + info +
                ", host='" + host + '\'' +
                ", basePath='" + basePath + '\'' +
                ", schemes=" + schemes +
                ", consumes=" + consumes +
                ", produces=" + produces +
                ", paths=" + paths +
                ", definitions=" + definitions +
                ", parameters=" + parameters +
                ", responses=" + responses +
                ", securityDefinitions=" + securityDefinitions +
                ", security=" + security +
                ", tags=" + tags +
                ", externalDocs=" + externalDocs +
                '}';
    }
}
