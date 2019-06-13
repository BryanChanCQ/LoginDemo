package com.cqns.demo.web.vo;

import com.cqns.demo.dao.entity.HandleEventDetails;

/**
 * @Author BryanChan
 * @Date 2019-06-11 14:24
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public class HandleEventDetailsVo extends HandleEventDetails {
    private int pageNum = 1;
    private int pageSize = 10;
    private int page = 0;
    private String showHandleEventGroup;
    private String showHandleEventStaff;
    private String showHandleCategories;
    private String showSystem;
    private String showDemand;
    private String showTestCover;
    private String showOptimizeCategories;
    private String showCreatedBy;


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getShowHandleEventGroup() {
        return showHandleEventGroup;
    }

    public void setShowHandleEventGroup(String showHandleEventGroup) {
        this.showHandleEventGroup = showHandleEventGroup;
    }

    public String getShowHandleEventStaff() {
        return showHandleEventStaff;
    }

    public void setShowHandleEventStaff(String showHandleEventStaff) {
        this.showHandleEventStaff = showHandleEventStaff;
    }

    public String getShowHandleCategories() {
        return showHandleCategories;
    }

    public void setShowHandleCategories(String showHandleCategories) {
        this.showHandleCategories = showHandleCategories;
    }

    public String getShowSystem() {
        return showSystem;
    }

    public void setShowSystem(String showSystem) {
        this.showSystem = showSystem;
    }

    public String getShowDemand() {
        return showDemand;
    }

    public void setShowDemand(String showDemand) {
        this.showDemand = showDemand;
    }

    public String getShowTestCover() {
        return showTestCover;
    }

    public void setShowTestCover(String showTestCover) {
        this.showTestCover = showTestCover;
    }

    public String getShowOptimizeCategories() {
        return showOptimizeCategories;
    }

    public void setShowOptimizeCategories(String showOptimizeCategories) {
        this.showOptimizeCategories = showOptimizeCategories;
    }

    public String getShowCreatedBy() {
        return showCreatedBy;
    }

    public void setShowCreatedBy(String showCreatedBy) {
        this.showCreatedBy = showCreatedBy;
    }
}
