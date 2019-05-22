package com.cqns.demo.web.vo;

import com.cqns.demo.dao.entity.UserRole;

public class UserRoleVo extends UserRole {
    private int pageNum = 1;
    private int pageSize = 10;
    private int page = 0;

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
