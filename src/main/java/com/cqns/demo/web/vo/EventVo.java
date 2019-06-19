package com.cqns.demo.web.vo;

import com.cqns.demo.dao.entity.Event;

/**
 * @Author BryanChan
 * @Date 2019-06-05 12:39
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public class EventVo extends Event {
    private int pageNum = 1;
    private int pageSize = 10;
    private int page = 0;
    private String showInstitution;
    private String showHandleEventGroup;
    private String showHandleEventStaff;
    private String showPriorityLevel;
    private String showEventType;
    private String showStatus;

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

    public String getShowInstitution() {
        return showInstitution;
    }

    public void setShowInstitution(String showInstitution) {
        this.showInstitution = showInstitution;
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

    public String getShowPriorityLevel() {
        return showPriorityLevel;
    }

    public void setShowPriorityLevel(String showPriorityLevel) {
        this.showPriorityLevel = showPriorityLevel;
    }

    public String getShowEventType() {
        return showEventType;
    }

    public void setShowEventType(String showEventType) {
        this.showEventType = showEventType;
    }

    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }
}
