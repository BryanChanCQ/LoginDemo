package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_user_role", schema = "cqns", catalog = "")
public class UserRole {
    private Long id;
    private Long UserId;
    private Long RoleId;
    private String UserName;
    private String RoleName;
    private Timestamp RawUpdateTime;
    private Timestamp RawAddTime;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "f_user_id")
    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    @Basic
    @Column(name = "f_role_id")
    public Long getRoleId() {
        return RoleId;
    }

    public void setRoleId(Long roleId) {
        RoleId = roleId;
    }

    @Basic
    @Column(name = "f_user_name")
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @Basic
    @Column(name = "f_role_name")
    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    @Basic
    @Column(name = "f_raw_update_time")
    public Timestamp getRawUpdateTime() {
        return RawUpdateTime;
    }

    public void setRawUpdateTime(Timestamp rawUpdateTime) {
        RawUpdateTime = rawUpdateTime;
    }

    @Basic
    @Column(name = "f_raw_add_time")
    public Timestamp getRawAddTime() {
        return RawAddTime;
    }

    public void setRawAddTime(Timestamp rawAddTime) {
        RawAddTime = rawAddTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (id != userRole.id) return false;
        if (UserId != userRole.UserId) return false;
        if (RoleId != userRole.RoleId) return false;
        if (UserName != null ? !UserName.equals(userRole.UserName) : userRole.UserName != null) return false;
        if (RoleName != null ? !RoleName.equals(userRole.RoleName) : userRole.RoleName != null) return false;
        if (RawUpdateTime != null ? !RawUpdateTime.equals(userRole.RawUpdateTime) : userRole.RawUpdateTime != null)
            return false;
        if (RawAddTime != null ? !RawAddTime.equals(userRole.RawAddTime) : userRole.RawAddTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (UserId ^ (UserId >>> 32));
        result = 31 * result + (int) (RoleId ^ (RoleId >>> 32));
        result = 31 * result + (UserName != null ? UserName.hashCode() : 0);
        result = 31 * result + (RoleName != null ? RoleName.hashCode() : 0);
        result = 31 * result + (RawUpdateTime != null ? RawUpdateTime.hashCode() : 0);
        result = 31 * result + (RawAddTime != null ? RawAddTime.hashCode() : 0);
        return result;
    }
}
