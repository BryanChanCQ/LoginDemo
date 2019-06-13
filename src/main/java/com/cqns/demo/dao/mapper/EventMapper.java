package com.cqns.demo.dao.mapper;

import com.cqns.demo.dao.entity.Event;
import java.util.List;

public interface EventMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_event
     *
     * @mbg.generated Wed Jun 05 16:08:29 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_event
     *
     * @mbg.generated Wed Jun 05 16:08:29 CST 2019
     */
    int insert(Event record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_event
     *
     * @mbg.generated Wed Jun 05 16:08:29 CST 2019
     */
    Event selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_event
     *
     * @mbg.generated Wed Jun 05 16:08:29 CST 2019
     */
    List<Event> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_event
     *
     * @mbg.generated Wed Jun 05 16:08:29 CST 2019
     */
    int updateByPrimaryKey(Event record);
}