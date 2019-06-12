package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_issue_attachment", schema = "cqns", catalog = "")
public class IssueAttachment {
    private Long id;
    private String IssueIdentifier;
    private String AttachmentName;
    private String AttachmentPath;
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
    @Column(name = "f_issue_identifier")
    public String getIssueIdentifier() {
        return IssueIdentifier;
    }

    public void setIssueIdentifier(String issueIdentifier) {
        IssueIdentifier = issueIdentifier;
    }

    @Basic
    @Column(name = "f_attachment_name")
    public String getAttachmentName() {
        return AttachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        AttachmentName = attachmentName;
    }

    @Basic
    @Column(name = "f_attachment_path")
    public String getAttachmentPath() {
        return AttachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        AttachmentPath = attachmentPath;
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

        IssueAttachment that = (IssueAttachment) o;

        if (id != that.id) return false;
        if (IssueIdentifier != null ? !IssueIdentifier.equals(that.IssueIdentifier) : that.IssueIdentifier != null)
            return false;
        if (AttachmentName != null ? !AttachmentName.equals(that.AttachmentName) : that.AttachmentName != null)
            return false;
        if (AttachmentPath != null ? !AttachmentPath.equals(that.AttachmentPath) : that.AttachmentPath != null)
            return false;
        if (RawUpdateTime != null ? !RawUpdateTime.equals(that.RawUpdateTime) : that.RawUpdateTime != null)
            return false;
        if (RawAddTime != null ? !RawAddTime.equals(that.RawAddTime) : that.RawAddTime != null) return false;
        if (CreatedBy != null ? !CreatedBy.equals(that.CreatedBy) : that.CreatedBy != null) return false;
        if (UpdateBy != null ? !UpdateBy.equals(that.UpdateBy) : that.UpdateBy != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (IssueIdentifier != null ? IssueIdentifier.hashCode() : 0);
        result = 31 * result + (AttachmentName != null ? AttachmentName.hashCode() : 0);
        result = 31 * result + (AttachmentPath != null ? AttachmentPath.hashCode() : 0);
        result = 31 * result + (RawUpdateTime != null ? RawUpdateTime.hashCode() : 0);
        result = 31 * result + (RawAddTime != null ? RawAddTime.hashCode() : 0);
        result = 31 * result + (CreatedBy != null ? CreatedBy.hashCode() : 0);
        result = 31 * result + (UpdateBy != null ? UpdateBy.hashCode() : 0);
        return result;
    }
}
