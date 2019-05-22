package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_user", schema = "cqns", catalog = "")
public class User {
    private long id;
    private String username;
    private String displayName;
    private String password;
    private boolean enabled;
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
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "display_name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "enabled")
    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

        User user = (User) o;

        if (id != user.id) return false;
        if (enabled != user.enabled) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (displayName != null ? !displayName.equals(user.displayName) : user.displayName != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (rawUpdateTime != null ? !rawUpdateTime.equals(user.rawUpdateTime) : user.rawUpdateTime != null)
            return false;
        if (rawAddTime != null ? !rawAddTime.equals(user.rawAddTime) : user.rawAddTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (rawUpdateTime != null ? rawUpdateTime.hashCode() : 0);
        result = 31 * result + (rawAddTime != null ? rawAddTime.hashCode() : 0);
        return result;
    }
}
