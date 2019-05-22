package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_role", schema = "cqns", catalog = "")
public class Role {
    private long id;
    private String name;
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

        Role role = (Role) o;

        if (id != role.id) return false;
        if (name != null ? !name.equals(role.name) : role.name != null) return false;
        if (rawUpdateTime != null ? !rawUpdateTime.equals(role.rawUpdateTime) : role.rawUpdateTime != null)
            return false;
        if (rawAddTime != null ? !rawAddTime.equals(role.rawAddTime) : role.rawAddTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rawUpdateTime != null ? rawUpdateTime.hashCode() : 0);
        result = 31 * result + (rawAddTime != null ? rawAddTime.hashCode() : 0);
        return result;
    }
}
