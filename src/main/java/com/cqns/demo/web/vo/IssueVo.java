package com.cqns.demo.web.vo;

import com.cqns.demo.dao.entity.Issue;

/**
 * @Author BryanChan
 * @Date 2019/5/22 17:06
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public class IssueVo extends Issue {
    private int pageNum = 1;
    private int pageSize = 10;
    private int page = 0;
    private String showInstitution;
    private String showHandleIssueGroup;
    private String showHandleIssueStaff;
    private String showPriorityLevel;

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

    public String getShowHandleIssueGroup() {
        return showHandleIssueGroup;
    }

    public void setShowHandleIssueGroup(String showHandleIssueGroup) {
        this.showHandleIssueGroup = showHandleIssueGroup;
    }

    public String getShowHandleIssueStaff() {
        return showHandleIssueStaff;
    }

    public void setShowHandleIssueStaff(String showHandleIssueStaff) {
        this.showHandleIssueStaff = showHandleIssueStaff;
    }

    public String getShowPriorityLevel() {
        return showPriorityLevel;
    }

    public void setShowPriorityLevel(String showPriorityLevel) {
        this.showPriorityLevel = showPriorityLevel;
    }
}
