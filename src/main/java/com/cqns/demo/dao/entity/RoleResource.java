package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_role_resource", schema = "cqns", catalog = "")
public class RoleResource {
    private long id;
    private long roleId;
    private long resourceId;
    private Integer resourceType;
    private String resourceName;
    private Timestamp rawUpdateTime;
    private Timestamp rawAddTime;
    private String roleName;
    private String resourceTypeName;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "resource_id")
    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    @Basic
    @Column(name = "resource_type")
    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    @Basic
    @Column(name = "resource_name")
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
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

    @Basic
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "resource_type_name")
    public String getResourceTypeName() {
        return resourceTypeName;
    }

    public void setResourceTypeName(String resourceTypeName) {
        this.resourceTypeName = resourceTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleResource that = (RoleResource) o;

        if (id != that.id) return false;
        if (roleId != that.roleId) return false;
        if (resourceId != that.resourceId) return false;
        if (resourceType != null ? !resourceType.equals(that.resourceType) : that.resourceType != null) return false;
        if (resourceName != null ? !resourceName.equals(that.resourceName) : that.resourceName != null) return false;
        if (rawUpdateTime != null ? !rawUpdateTime.equals(that.rawUpdateTime) : that.rawUpdateTime != null)
            return false;
        if (rawAddTime != null ? !rawAddTime.equals(that.rawAddTime) : that.rawAddTime != null) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;
        if (resourceTypeName != null ? !resourceTypeName.equals(that.resourceTypeName) : that.resourceTypeName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (int) (resourceId ^ (resourceId >>> 32));
        result = 31 * result + (resourceType != null ? resourceType.hashCode() : 0);
        result = 31 * result + (resourceName != null ? resourceName.hashCode() : 0);
        result = 31 * result + (rawUpdateTime != null ? rawUpdateTime.hashCode() : 0);
        result = 31 * result + (rawAddTime != null ? rawAddTime.hashCode() : 0);
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (resourceTypeName != null ? resourceTypeName.hashCode() : 0);
        return result;
    }
}
