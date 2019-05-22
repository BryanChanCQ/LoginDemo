package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_user_role", schema = "cqns", catalog = "")
public class UserRole {
    private long id;
    private long userId;
    private long roleId;
    private String userName;
    private String roleName;
    private Timestamp rawUpdateTime;
    private Timestamp rawAddTime;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "role_id")
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "raw_update_time")
    public Timestamp getRawUpdateTime() {
        return rawUpdateTime;
    }

    public void setRawUpdateTime(Timestamp rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }

    @Basic
    @Column(name = "raw_add_time")
    public Timestamp getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Timestamp rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (id != userRole.id) return false;
        if (userId != userRole.userId) return false;
        if (roleId != userRole.roleId) return false;
        if (userName != null ? !userName.equals(userRole.userName) : userRole.userName != null) return false;
        if (roleName != null ? !roleName.equals(userRole.roleName) : userRole.roleName != null) return false;
        if (rawUpdateTime != null ? !rawUpdateTime.equals(userRole.rawUpdateTime) : userRole.rawUpdateTime != null)
            return false;
        if (rawAddTime != null ? !rawAddTime.equals(userRole.rawAddTime) : userRole.rawAddTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (rawUpdateTime != null ? rawUpdateTime.hashCode() : 0);
        result = 31 * result + (rawAddTime != null ? rawAddTime.hashCode() : 0);
        return result;
    }
}
