package com.cqns.demo.dao.mapper;

import com.cqns.demo.dao.entity.EventAttachment;
import java.util.List;

public interface EventAttachmentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_event_attachment
     *
     * @mbg.generated Wed Jun 05 14:00:15 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_event_attachment
     *
     * @mbg.generated Wed Jun 05 14:00:15 CST 2019
     */
    int insert(EventAttachment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_event_attachment
     *
     * @mbg.generated Wed Jun 05 14:00:15 CST 2019
     */
    EventAttachment selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_event_attachment
     *
     * @mbg.generated Wed Jun 05 14:00:15 CST 2019
     */
    List<EventAttachment> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_event_attachment
     *
     * @mbg.generated Wed Jun 05 14:00:15 CST 2019
     */
    int updateByPrimaryKey(EventAttachment record);
}