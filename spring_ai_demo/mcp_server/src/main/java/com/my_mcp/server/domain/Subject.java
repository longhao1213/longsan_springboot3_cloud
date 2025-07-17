package com.my_mcp.server.domain;

import lombok.Data;

import java.util.List;

public class Subject {

    private String type;

    private List<String> subjects;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
//        return "Subject{" +
//                "type='" + type + '\'' +
//                ", subjects=" + subjects +
//                '}';

        return "Subject{" + "type='" + type + '\'' + ", subjects=" + subjects + '}';
    }
}
