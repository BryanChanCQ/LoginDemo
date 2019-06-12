package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_issue", schema = "cqns", catalog = "")
public class Issue {
    private Long id;
    private String IssueTitle;
    private String IssueIdentifier;
    private String IssueReporter;
    private String Contact;
    private String Institution;
    private Timestamp IssueCreateDate;
    private String Description;
    private String PriorityLevel;
    private String HandleIssueGroup;
    private String HandleIssueStaff;
    private String Status;
    private Timestamp RawUpdateTime;
    private Timestamp RawAddTime;
    private String CreatedBy;
    private String UpdateBy;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "f_issue_title")
    public String getIssueTitle() {
        return IssueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        IssueTitle = issueTitle;
    }

    @Basic
    @Column(name = "f_issue_identifier")
    public String getIssueIdentifier() {
        return IssueIdentifier;
    }

    public void setIssueIdentifier(String issueIdentifier) {
        IssueIdentifier = issueIdentifier;
    }

    @Basic
    @Column(name = "f_issue_reporter")
    public String getIssueReporter() {
        return IssueReporter;
    }

    public void setIssueReporter(String issueReporter) {
        IssueReporter = issueReporter;
    }

    @Basic
    @Column(name = "f_contact")
    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    @Basic
    @Column(name = "f_institution")
    public String getInstitution() {
        return Institution;
    }

    public void setInstitution(String institution) {
        Institution = institution;
    }

    @Basic
    @Column(name = "f_issue_create_date")
    public Timestamp getIssueCreateDate() {
        return IssueCreateDate;
    }

    public void setIssueCreateDate(Timestamp issueCreateDate) {
        IssueCreateDate = issueCreateDate;
    }

    @Basic
    @Column(name = "f_description")
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Basic
    @Column(name = "f_priority_level")
    public String getPriorityLevel() {
        return PriorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        PriorityLevel = priorityLevel;
    }

    @Basic
    @Column(name = "f_handle_Issue_group")
    public String getHandleIssueGroup() {
        return HandleIssueGroup;
    }

    public void setHandleIssueGroup(String handleIssueGroup) {
        HandleIssueGroup = handleIssueGroup;
    }

    @Basic
    @Column(name = "f_handle_Issue_staff")
    public String getHandleIssueStaff() {
        return HandleIssueStaff;
    }

    public void setHandleIssueStaff(String handleIssueStaff) {
        HandleIssueStaff = handleIssueStaff;
    }

    @Basic
    @Column(name = "f_status")
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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

    @Basic
    @Column(name = "f_created_By")
    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    @Basic
    @Column(name = "f_update_By")
    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        if (id != issue.id) return false;
        if (IssueTitle != null ? !IssueTitle.equals(issue.IssueTitle) : issue.IssueTitle != null) return false;
        if (IssueIdentifier != null ? !IssueIdentifier.equals(issue.IssueIdentifier) : issue.IssueIdentifier != null)
            return false;
        if (IssueReporter != null ? !IssueReporter.equals(issue.IssueReporter) : issue.IssueReporter != null)
            return false;
        if (Contact != null ? !Contact.equals(issue.Contact) : issue.Contact != null) return false;
        if (Institution != null ? !Institution.equals(issue.Institution) : issue.Institution != null) return false;
        if (IssueCreateDate != null ? !IssueCreateDate.equals(issue.IssueCreateDate) : issue.IssueCreateDate != null)
            return false;
        if (Description != null ? !Description.equals(issue.Description) : issue.Description != null) return false;
        if (PriorityLevel != null ? !PriorityLevel.equals(issue.PriorityLevel) : issue.PriorityLevel != null)
            return false;
        if (HandleIssueGroup != null ? !HandleIssueGroup.equals(issue.HandleIssueGroup) : issue.HandleIssueGroup != null)
            return false;
        if (HandleIssueStaff != null ? !HandleIssueStaff.equals(issue.HandleIssueStaff) : issue.HandleIssueStaff != null)
            return false;
        if (Status != null ? !Status.equals(issue.Status) : issue.Status != null) return false;
        if (RawUpdateTime != null ? !RawUpdateTime.equals(issue.RawUpdateTime) : issue.RawUpdateTime != null)
            return false;
        if (RawAddTime != null ? !RawAddTime.equals(issue.RawAddTime) : issue.RawAddTime != null) return false;
        if (CreatedBy != null ? !CreatedBy.equals(issue.CreatedBy) : issue.CreatedBy != null) return false;
        if (UpdateBy != null ? !UpdateBy.equals(issue.UpdateBy) : issue.UpdateBy != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (IssueTitle != null ? IssueTitle.hashCode() : 0);
        result = 31 * result + (IssueIdentifier != null ? IssueIdentifier.hashCode() : 0);
        result = 31 * result + (IssueReporter != null ? IssueReporter.hashCode() : 0);
        result = 31 * result + (Contact != null ? Contact.hashCode() : 0);
        result = 31 * result + (Institution != null ? Institution.hashCode() : 0);
        result = 31 * result + (IssueCreateDate != null ? IssueCreateDate.hashCode() : 0);
        result = 31 * result + (Description != null ? Description.hashCode() : 0);
        result = 31 * result + (PriorityLevel != null ? PriorityLevel.hashCode() : 0);
        result = 31 * result + (HandleIssueGroup != null ? HandleIssueGroup.hashCode() : 0);
        result = 31 * result + (HandleIssueStaff != null ? HandleIssueStaff.hashCode() : 0);
        result = 31 * result + (Status != null ? Status.hashCode() : 0);
        result = 31 * result + (RawUpdateTime != null ? RawUpdateTime.hashCode() : 0);
        result = 31 * result + (RawAddTime != null ? RawAddTime.hashCode() : 0);
        result = 31 * result + (CreatedBy != null ? CreatedBy.hashCode() : 0);
        result = 31 * result + (UpdateBy != null ? UpdateBy.hashCode() : 0);
        return result;
    }
}
