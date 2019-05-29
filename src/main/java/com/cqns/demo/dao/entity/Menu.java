package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_menu", schema = "cqns", catalog = "")
public class Menu {
    private long id;
    private String Name;
    private long ParentId;
    private String ParentName;
    private String Icon;
    private int OrderNum;
    private String Url;
    private Integer Type;
    private Timestamp RawUpdateTime;
    private Timestamp RawAddTime;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "f_name")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Basic
    @Column(name = "f_parent_id")
    public long getParentId() {
        return ParentId;
    }

    public void setParentId(long parentId) {
        ParentId = parentId;
    }

    @Basic
    @Column(name = "f_parent_name")
    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String parentName) {
        ParentName = parentName;
    }

    @Basic
    @Column(name = "f_icon")
    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    @Basic
    @Column(name = "f_order_num")
    public int getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(int orderNum) {
        OrderNum = orderNum;
    }

    @Basic
    @Column(name = "f_url")
    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Basic
    @Column(name = "f_type")
    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
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

        Menu menu = (Menu) o;

        if (id != menu.id) return false;
        if (ParentId != menu.ParentId) return false;
        if (OrderNum != menu.OrderNum) return false;
        if (Name != null ? !Name.equals(menu.Name) : menu.Name != null) return false;
        if (ParentName != null ? !ParentName.equals(menu.ParentName) : menu.ParentName != null) return false;
        if (Icon != null ? !Icon.equals(menu.Icon) : menu.Icon != null) return false;
        if (Url != null ? !Url.equals(menu.Url) : menu.Url != null) return false;
        if (Type != null ? !Type.equals(menu.Type) : menu.Type != null) return false;
        if (RawUpdateTime != null ? !RawUpdateTime.equals(menu.RawUpdateTime) : menu.RawUpdateTime != null)
            return false;
        if (RawAddTime != null ? !RawAddTime.equals(menu.RawAddTime) : menu.RawAddTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (Name != null ? Name.hashCode() : 0);
        result = 31 * result + (int) (ParentId ^ (ParentId >>> 32));
        result = 31 * result + (ParentName != null ? ParentName.hashCode() : 0);
        result = 31 * result + (Icon != null ? Icon.hashCode() : 0);
        result = 31 * result + OrderNum;
        result = 31 * result + (Url != null ? Url.hashCode() : 0);
        result = 31 * result + (Type != null ? Type.hashCode() : 0);
        result = 31 * result + (RawUpdateTime != null ? RawUpdateTime.hashCode() : 0);
        result = 31 * result + (RawAddTime != null ? RawAddTime.hashCode() : 0);
        return result;
    }
}
