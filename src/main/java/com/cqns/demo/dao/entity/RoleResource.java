package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_role_resource", schema = "cqns", catalog = "")
public class RoleResource {
    private long id;
    private long RoleId;
    private long ResourceId;
    private Integer ResourceType;
    private String ResourceName;
    private Timestamp RawUpdateTime;
    private Timestamp RawAddTime;
    private String RoleName;
    private String ResourceTypeName;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "f_role_id")
    public long getRoleId() {
        return RoleId;
    }

    public void setRoleId(long roleId) {
        RoleId = roleId;
    }

    @Basic
    @Column(name = "f_resource_id")
    public long getResourceId() {
        return ResourceId;
    }

    public void setResourceId(long resourceId) {
        ResourceId = resourceId;
    }

    @Basic
    @Column(name = "f_resource_type")
    public Integer getResourceType() {
        return ResourceType;
    }

    public void setResourceType(Integer resourceType) {
        ResourceType = resourceType;
    }

    @Basic
    @Column(name = "f_resource_name")
    public String getResourceName() {
        return ResourceName;
    }

    public void setResourceName(String resourceName) {
        ResourceName = resourceName;
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

    @Basic
    @Column(name = "f_role_name")
    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    @Basic
    @Column(name = "f_resource_type_name")
    public String getResourceTypeName() {
        return ResourceTypeName;
    }

    public void setResourceTypeName(String resourceTypeName) {
        ResourceTypeName = resourceTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleResource that = (RoleResource) o;

        if (id != that.id) return false;
        if (RoleId != that.RoleId) return false;
        if (ResourceId != that.ResourceId) return false;
        if (ResourceType != null ? !ResourceType.equals(that.ResourceType) : that.ResourceType != null) return false;
        if (ResourceName != null ? !ResourceName.equals(that.ResourceName) : that.ResourceName != null) return false;
        if (RawUpdateTime != null ? !RawUpdateTime.equals(that.RawUpdateTime) : that.RawUpdateTime != null)
            return false;
        if (RawAddTime != null ? !RawAddTime.equals(that.RawAddTime) : that.RawAddTime != null) return false;
        if (RoleName != null ? !RoleName.equals(that.RoleName) : that.RoleName != null) return false;
        if (ResourceTypeName != null ? !ResourceTypeName.equals(that.ResourceTypeName) : that.ResourceTypeName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (RoleId ^ (RoleId >>> 32));
        result = 31 * result + (int) (ResourceId ^ (ResourceId >>> 32));
        result = 31 * result + (ResourceType != null ? ResourceType.hashCode() : 0);
        result = 31 * result + (ResourceName != null ? ResourceName.hashCode() : 0);
        result = 31 * result + (RawUpdateTime != null ? RawUpdateTime.hashCode() : 0);
        result = 31 * result + (RawAddTime != null ? RawAddTime.hashCode() : 0);
        result = 31 * result + (RoleName != null ? RoleName.hashCode() : 0);
        result = 31 * result + (ResourceTypeName != null ? ResourceTypeName.hashCode() : 0);
        return result;
    }
}
