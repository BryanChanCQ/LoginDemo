package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_user", schema = "cqns", catalog = "")
public class User {
    private long id;
    private String Username;
    private String DisplayName;
    private String Password;
    private Boolean Enabled;
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
    @Column(name = "f_username")
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    @Basic
    @Column(name = "f_display_name")
    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    @Basic
    @Column(name = "f_password")
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Basic
    @Column(name = "f_enabled")
    public Boolean getEnabled() {
        return Enabled;
    }

    public void setEnabled(Boolean enabled) {
        Enabled = enabled;
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

        User user = (User) o;

        if (id != user.id) return false;
        if (Enabled != user.Enabled) return false;
        if (Username != null ? !Username.equals(user.Username) : user.Username != null) return false;
        if (DisplayName != null ? !DisplayName.equals(user.DisplayName) : user.DisplayName != null) return false;
        if (Password != null ? !Password.equals(user.Password) : user.Password != null) return false;
        if (RawUpdateTime != null ? !RawUpdateTime.equals(user.RawUpdateTime) : user.RawUpdateTime != null)
            return false;
        if (RawAddTime != null ? !RawAddTime.equals(user.RawAddTime) : user.RawAddTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (Username != null ? Username.hashCode() : 0);
        result = 31 * result + (DisplayName != null ? DisplayName.hashCode() : 0);
        result = 31 * result + (Password != null ? Password.hashCode() : 0);
        result = 31 * result + (Enabled ? 1 : 0);
        result = 31 * result + (RawUpdateTime != null ? RawUpdateTime.hashCode() : 0);
        result = 31 * result + (RawAddTime != null ? RawAddTime.hashCode() : 0);
        return result;
    }
}
