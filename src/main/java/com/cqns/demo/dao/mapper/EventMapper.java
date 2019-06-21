package com.cqns.demo.dao.mapper;

import com.cqns.demo.dao.entity.Event;
import com.cqns.demo.web.vo.EventVo;

import java.util.HashMap;
import java.util.List;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public interface EventMapper {
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(Event record);

    /**
     * 查询
     * @param id
     * @return
     */
    Event selectByPrimaryKey(Long id);

    /**
     * 查询所有
     * @return
     */
    List<Event> selectAll();

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Event record);

    /**
     * 查询事件历史处理结果
     * @param map
     * @return
     */
    List<Event> getHandleEventHistoryRecord(HashMap map);

    /**
     * 查询分配给我的事件
     * @param map
     * @return
     */
    List<Event> findDistributeToMe(HashMap map);
}