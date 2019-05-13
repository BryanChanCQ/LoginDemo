package com.cqns.demo.dao.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Long id;

    private String name;

    /**
     * 父id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 父菜单名
     */
    @Column(name = "parent_name")
    private String parentName;

    /**
     * 图标
     */
    private String icon;

    /**
     * 顺序
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * url
     */
    private String url;

    /**
     * 菜单类型
     */
    private Integer type;

    @Column(name = "raw_update_time")
    private Date rawUpdateTime;

    @Column(name = "raw_add_time")
    private Date rawAddTime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取父id
     *
     * @return parent_id - 父id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父id
     *
     * @param parentId 父id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取父菜单名
     *
     * @return parent_name - 父菜单名
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * 设置父菜单名
     *
     * @param parentName 父菜单名
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * 获取图标
     *
     * @return icon - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取顺序
     *
     * @return order_num - 顺序
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置顺序
     *
     * @param orderNum 顺序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取url
     *
     * @return url - url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url
     *
     * @param url url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取菜单类型
     *
     * @return type - 菜单类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置菜单类型
     *
     * @param type 菜单类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return raw_update_time
     */
    public Date getRawUpdateTime() {
        return rawUpdateTime;
    }

    /**
     * @param rawUpdateTime
     */
    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }

    /**
     * @return raw_add_time
     */
    public Date getRawAddTime() {
        return rawAddTime;
    }

    /**
     * @param rawAddTime
     */
    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }
}