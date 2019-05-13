package com.cqns.demo.web.vo;

import com.cqns.demo.dao.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserVo extends User{
    private int pageNum = 1;
    private int pageSize = 10;
    private int page = 1;
    private List<RoleVo> roles;

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


    public List<RoleVo> getRoles() {
        return this.roles;
    }

    public void setRoles(List<RoleVo> roles) {
        this.roles = roles;
    }

}
