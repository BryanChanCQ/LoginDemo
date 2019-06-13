package com.cqns.demo.dao.mapper;

import com.cqns.demo.dao.entity.HandleEventDetails;
import java.util.List;

public interface HandleEventDetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_handle_event_details
     *
     * @mbg.generated Fri Jun 07 09:09:39 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_handle_event_details
     *
     * @mbg.generated Fri Jun 07 09:09:39 CST 2019
     */
    int insert(HandleEventDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_handle_event_details
     *
     * @mbg.generated Fri Jun 07 09:09:39 CST 2019
     */
    HandleEventDetails selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_handle_event_details
     *
     * @mbg.generated Fri Jun 07 09:09:39 CST 2019
     */
    List<HandleEventDetails> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_handle_event_details
     *
     * @mbg.generated Fri Jun 07 09:09:39 CST 2019
     */
    int updateByPrimaryKey(HandleEventDetails record);
}