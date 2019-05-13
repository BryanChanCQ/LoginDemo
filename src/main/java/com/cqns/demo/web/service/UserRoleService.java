package com.cqns.demo.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.baseMap.MyBaseMapper;
import com.cqns.demo.dao.entity.UserRole;
import com.cqns.demo.dao.mapper.UserRoleMapper;
import com.cqns.demo.web.vo.UserRoleVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService extends AbstractCommonService<UserRole> {
    private static Logger logger = LoggerFactory.getLogger(UserRoleService.class);
    @Resource
    private UserRoleMapper userRoleMapper;
    @Override
    protected MyBaseMapper<UserRole> mapper() {
        return userRoleMapper;
    }

    public List<UserRoleVo> userRoleVoList(UserRoleVo userRoleVo){

        Example example = new Example(UserRole.class);

        example.orderBy("rawUpdateTime").desc();

        Example.Criteria criteria = example.createCriteria();

        if (!Strings.isNullOrEmpty(userRoleVo.getRoleName())){

            criteria.andLike("roleName","%" + userRoleVo.getRoleName() + "%");

        }

        if (!Strings.isNullOrEmpty(userRoleVo.getUserName())){

            criteria.andLike("userName","%" + userRoleVo.getUserName() + "%");

        }

        if (!Strings.isNullOrEmpty(String.valueOf(userRoleVo.getUserId()))){

            criteria.andEqualTo("userId", userRoleVo.getUserId());

        }

        List<UserRole> userRoles = this.list(example);

        return JSON.parseObject(JSON.toJSONString(userRoles), new TypeReference<List<UserRoleVo>>(){}.getType());
    }

    public PageInfo<UserRoleVo> userRoleVoPageInfo(UserRoleVo userRoleVo){

        Page<UserRoleVo> page = PageHelper.startPage(userRoleVo.getPage(), userRoleVo.getPageSize());

        Example example = new Example(UserRole.class);

        example.orderBy("rawUpdateTime").desc();

        Example.Criteria criteria = example.createCriteria();

        if (!Strings.isNullOrEmpty(userRoleVo.getUserName())){

            criteria.andLike("userName","%" + userRoleVo.getUserName() + "%");

        }

        if (!Strings.isNullOrEmpty(userRoleVo.getRoleName())){

            criteria.andLike("roleName","%" + userRoleVo.getRoleName() + "%");

        }

        this.list(example);

        return new PageInfo<>(page);
    }

    public boolean updateUserRole(UserRoleVo userRoleVo){


        Example example = new Example(UserRole.class);

        Example.Criteria criteria = example.createCriteria();

        if (Optional.ofNullable(userRoleVo.getRoleId()).isPresent()){

            criteria.andEqualTo("roleId", userRoleVo.getRoleId());

        }

        return this.updateByExampleSelective(userRoleVo,example);

    }
}
