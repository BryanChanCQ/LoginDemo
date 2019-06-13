package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Author BryanChan
 * @Date 2019-06-07 09:13
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Entity
@Table(name = "t_handle_event_details", schema = "cqns", catalog = "")
public class HandleEventDetails {
    private long id;
    private String eventIdentifier;
    private String eventId;
    private long handleEventGroup;
    private String handleEventStaff;
    private String handleCategories;
    private String handleCondition;
    private long system;
    private String demand;
    private String testCover;
    private String testExplanation;
    private String optimizeCategories;
    private String optimizePlan;
    private String createdBy;

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
    @Column(name = "f_event_identifier")
    public String getEventIdentifier() {
        return eventIdentifier;
    }

    public void setEventIdentifier(String eventIdentifier) {
        this.eventIdentifier = eventIdentifier;
    }

    @Basic
    @Column(name = "f_event_id")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Basic
    @Column(name = "f_handle_event_group")
    public long getHandleEventGroup() {
        return handleEventGroup;
    }

    public void setHandleEventGroup(long handleEventGroup) {
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
    @Column(name = "f_handle_categories")
    public String getHandleCategories() {
        return handleCategories;
    }

    public void setHandleCategories(String handleCategories) {
        this.handleCategories = handleCategories;
    }

    @Basic
    @Column(name = "f_handle_condition")
    public String getHandleCondition() {
        return handleCondition;
    }

    public void setHandleCondition(String handleCondition) {
        this.handleCondition = handleCondition;
    }

    @Basic
    @Column(name = "f_system")
    public long getSystem() {
        return system;
    }

    public void setSystem(long system) {
        this.system = system;
    }

    @Basic
    @Column(name = "f_demand")
    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    @Basic
    @Column(name = "f_test_cover")
    public String getTestCover() {
        return testCover;
    }

    public void setTestCover(String testCover) {
        this.testCover = testCover;
    }

    @Basic
    @Column(name = "f_test_explanation")
    public String getTestExplanation() {
        return testExplanation;
    }

    public void setTestExplanation(String testExplanation) {
        this.testExplanation = testExplanation;
    }

    @Basic
    @Column(name = "f_optimize_categories")
    public String getOptimizeCategories() {
        return optimizeCategories;
    }

    public void setOptimizeCategories(String optimizeCategories) {
        this.optimizeCategories = optimizeCategories;
    }

    @Basic
    @Column(name = "f_optimize_plan")
    public String getOptimizePlan() {
        return optimizePlan;
    }

    public void setOptimizePlan(String optimizePlan) {
        this.optimizePlan = optimizePlan;
    }

    @Basic
    @Column(name = "f_created_By")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandleEventDetails that = (HandleEventDetails) o;
        return id == that.id &&
                handleEventGroup == that.handleEventGroup &&
                system == that.system &&
                Objects.equals(eventIdentifier, that.eventIdentifier) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(handleEventStaff, that.handleEventStaff) &&
                Objects.equals(handleCategories, that.handleCategories) &&
                Objects.equals(handleCondition, that.handleCondition) &&
                Objects.equals(demand, that.demand) &&
                Objects.equals(testCover, that.testCover) &&
                Objects.equals(testExplanation, that.testExplanation) &&
                Objects.equals(optimizeCategories, that.optimizeCategories) &&
                Objects.equals(optimizePlan, that.optimizePlan) &&
                Objects.equals(createdBy, that.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventIdentifier, eventId, handleEventGroup, handleEventStaff, handleCategories, handleCondition, system, demand, testCover, testExplanation, optimizeCategories, optimizePlan, createdBy);
    }
}
