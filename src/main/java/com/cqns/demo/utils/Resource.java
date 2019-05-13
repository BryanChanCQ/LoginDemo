package com.cqns.demo.utils;

public enum Resource {
    MENU(1);

    private int type;

    private Resource(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}