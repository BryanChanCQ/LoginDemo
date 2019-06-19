package com.cqns.demo.utils;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public enum Resource {
    //菜单类型
    MENU(1);

    private int type;

    private Resource(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}