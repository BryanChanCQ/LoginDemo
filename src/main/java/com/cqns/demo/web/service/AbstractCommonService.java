/*
 * 修订记录:
 * yuanxiao@cqfmbank.com 2018/12/10 14:04 创建
 *
 */
package com.cqns.demo.web.service;

import com.cqns.demo.baseMap.MyBaseMapper;
import com.cqns.demo.dao.entity.Menu;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author BryanChan
 */
public abstract class AbstractCommonService<T> {

	public boolean add(T modal) {
		return mapper().insert(modal) == 1;
	}

	public boolean updateById(T modal) {
		return mapper().updateByPrimaryKeySelective(modal) == 1;
	}

	public boolean deleteById(Long id) {
		return mapper().deleteByPrimaryKey(id) == 1;
	}

	public T selectById(Long id) {
		return mapper().selectByPrimaryKey(id);
	}

	protected List<T> list(Example example) {
		return mapper().selectByExample(example);
	}

	public boolean deleteByExample(Example example){
		return mapper().deleteByExample(example) >= 0;
	}

	public boolean updateByExampleSelective(T modal, Example example){
		return mapper().updateByExampleSelective(modal,example) >= 0;
	}

	protected abstract MyBaseMapper<T> mapper();

}
