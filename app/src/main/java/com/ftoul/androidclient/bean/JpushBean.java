package com.ftoul.androidclient.bean;

import java.util.Set;

/**
 * Created by M on 2016/10/26.
 */

public class JpushBean {
    String aliens;
    Set<String> tags;
    public JpushBean() {

    }

    public String getAliens() {
        return aliens;
    }

    public void setAliens(String aliens) {
        this.aliens = aliens;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public JpushBean(String aliens, Set<String> tags) {

        this.aliens = aliens;
        this.tags = tags;
    }
}
