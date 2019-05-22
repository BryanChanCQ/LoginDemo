package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_menu", schema = "cqns", catalog = "")
public class Menu {
    private long id;
    private String name;
    private long parentId;
    private String parentName;
    private String icon;
    private int orderNum;
    private String url;
    private Integer type;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "parent_id")
    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "parent_name")
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Basic
    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "order_num")
    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

        Menu menu = (Menu) o;

        if (id != menu.id) return false;
        if (parentId != menu.parentId) return false;
        if (orderNum != menu.orderNum) return false;
        if (name != null ? !name.equals(menu.name) : menu.name != null) return false;
        if (parentName != null ? !parentName.equals(menu.parentName) : menu.parentName != null) return false;
        if (icon != null ? !icon.equals(menu.icon) : menu.icon != null) return false;
        if (url != null ? !url.equals(menu.url) : menu.url != null) return false;
        if (type != null ? !type.equals(menu.type) : menu.type != null) return false;
        if (rawUpdateTime != null ? !rawUpdateTime.equals(menu.rawUpdateTime) : menu.rawUpdateTime != null)
            return false;
        if (rawAddTime != null ? !rawAddTime.equals(menu.rawAddTime) : menu.rawAddTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (parentId ^ (parentId >>> 32));
        result = 31 * result + (parentName != null ? parentName.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + orderNum;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (rawUpdateTime != null ? rawUpdateTime.hashCode() : 0);
        result = 31 * result + (rawAddTime != null ? rawAddTime.hashCode() : 0);
        return result;
    }
}
