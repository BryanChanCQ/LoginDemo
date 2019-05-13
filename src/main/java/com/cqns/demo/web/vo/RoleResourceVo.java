package com.cqns.demo.web.vo;

import com.cqns.demo.dao.entity.RoleResource;

public class RoleResourceVo extends RoleResource {
    private int pageNum = 1;
    private int pageSize = 10;
    private int page = 1;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
