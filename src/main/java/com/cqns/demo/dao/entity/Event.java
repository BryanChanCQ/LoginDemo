package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Author BryanChan
 * @Date 2019-06-17 14:20
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Entity
@Table(name = "t_event", schema = "cqns", catalog = "")
public class Event {
    private long id;
    private String eventTitle;
    private String eventIdentifier;
    private String eventType;
    private String eventContactor;
    private String contact;
    private String institution;
    private Timestamp eventCreateDate;
    private String description;
    private String priorityLevel;
    private String handleEventGroup;
    private String handleEventStaff;
    private String status;
    private Timestamp rawUpdateTime;
    private Timestamp rawAddTime;
    private String createdBy;
    private String updateBy;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "f_event_title")
    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    @Basic
    @Column(name = "f_event_identifier")
    public String getEventIdentifier() {
        return eventIdentifier;
    }

    public void setEventIdentifier(String eventIdentifier) {
        this.eventIdentifier = eventIdentifier;
    }

    @Basic
    @Column(name = "f_event_type")
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Basic
    @Column(name = "f_event_contactor")
    public String getEventContactor() {
        return eventContactor;
    }

    public void setEventContactor(String eventContactor) {
        this.eventContactor = eventContactor;
    }

    @Basic
    @Column(name = "f_contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "f_institution")
    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @Basic
    @Column(name = "f_event_create_date")
    public Timestamp getEventCreateDate() {
        return eventCreateDate;
    }

    public void setEventCreateDate(Timestamp eventCreateDate) {
        this.eventCreateDate = eventCreateDate;
    }

    @Basic
    @Column(name = "f_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "f_priority_level")
    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    @Basic
    @Column(name = "f_handle_event_group")
    public String getHandleEventGroup() {
        return handleEventGroup;
    }

    public void setHandleEventGroup(String handleEventGroup) {
        this.handleEventGroup = handleEventGroup;
    }

    @Basic
    @Column(name = "f_handle_event_staff")
    public String getHandleEventStaff() {
        return handleEventStaff;
    }

    public void setHandleEventStaff(String handleEventStaff) {
        this.handleEventStaff = handleEventStaff;
    }

    @Basic
    @Column(name = "f_status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "f_raw_update_time")
    public Timestamp getRawUpdateTime() {
        return rawUpdateTime;
    }

    public void setRawUpdateTime(Timestamp rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }

    @Basic
    @Column(name = "f_raw_add_time")
    public Timestamp getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Timestamp rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    @Basic
    @Column(name = "f_created_By")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "f_update_By")
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id &&
                Objects.equals(eventTitle, event.eventTitle) &&
                Objects.equals(eventIdentifier, event.eventIdentifier) &&
                Objects.equals(eventType, event.eventType) &&
                Objects.equals(eventContactor, event.eventContactor) &&
                Objects.equals(contact, event.contact) &&
                Objects.equals(institution, event.institution) &&
                Objects.equals(eventCreateDate, event.eventCreateDate) &&
                Objects.equals(description, event.description) &&
                Objects.equals(priorityLevel, event.priorityLevel) &&
                Objects.equals(handleEventGroup, event.handleEventGroup) &&
                Objects.equals(handleEventStaff, event.handleEventStaff) &&
                Objects.equals(status, event.status) &&
                Objects.equals(rawUpdateTime, event.rawUpdateTime) &&
                Objects.equals(rawAddTime, event.rawAddTime) &&
                Objects.equals(createdBy, event.createdBy) &&
                Objects.equals(updateBy, event.updateBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventTitle, eventIdentifier, eventType, eventContactor, contact, institution, eventCreateDate, description, priorityLevel, handleEventGroup, handleEventStaff, status, rawUpdateTime, rawAddTime, createdBy, updateBy);
    }
}
