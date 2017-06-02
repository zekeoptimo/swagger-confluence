package com.zekeoptimo.swagger2confluence.model.swagger;

/**
 * Created by zekeo on 4/20/2017.
 */
public class Contact extends BaseAdditionalAttributes {
    private String name;
    private String url;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return ((name != null ? name : "") + " " + (url != null ? url : "") + (email != null ? email : "")).trim();
    }
}
