package com.cqns.demo.baseMap;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
/**
 * @author BryanChan
 */
public interface MyBaseMapper<T> extends Mapper<T>,MySqlMapper<T> {

}
