package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_role", schema = "cqns", catalog = "")
public class Role {
    private long id;
    private String Name;
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

        Role role = (Role) o;

        if (id != role.id) return false;
        if (Name != null ? !Name.equals(role.Name) : role.Name != null) return false;
        if (RawUpdateTime != null ? !RawUpdateTime.equals(role.RawUpdateTime) : role.RawUpdateTime != null)
            return false;
        if (RawAddTime != null ? !RawAddTime.equals(role.RawAddTime) : role.RawAddTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (Name != null ? Name.hashCode() : 0);
        result = 31 * result + (RawUpdateTime != null ? RawUpdateTime.hashCode() : 0);
        result = 31 * result + (RawAddTime != null ? RawAddTime.hashCode() : 0);
        return result;
    }
}
