package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Entity
@Table(name = "t_role_resource", schema = "cqns", catalog = "")
public class RoleResource {
    private Long id;
    private Long RoleId;
    private Long ResourceId;
    private Integer ResourceType;
    private String ResourceName;
    private Timestamp RawUpdateTime;
    private Timestamp RawAddTime;
    private String RoleName;
    private String ResourceTypeName;

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
    @Column(name = "f_role_id")
    public Long getRoleId() {
        return RoleId;
    }

    public void setRoleId(Long roleId) {
        RoleId = roleId;
    }

    @Basic
    @Column(name = "f_resource_id")
    public Long getResourceId() {
        return ResourceId;
    }

    public void setResourceId(Long resourceId) {
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

}
