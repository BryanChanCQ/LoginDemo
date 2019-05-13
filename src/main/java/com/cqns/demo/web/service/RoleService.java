package com.cqns.demo.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.baseMap.MyBaseMapper;
import com.cqns.demo.dao.entity.Role;
import com.cqns.demo.dao.entity.UserRole;
import com.cqns.demo.dao.mapper.RoleMapper;
import com.cqns.demo.dao.mapper.UserRoleMapper;
import com.cqns.demo.web.vo.RoleVo;
import com.cqns.demo.web.vo.UserRoleVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService extends AbstractCommonService<Role> {
    private static Logger logger = LoggerFactory.getLogger(RoleService.class);
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserRoleService userRoleService;
    @Override
    protected MyBaseMapper<Role> mapper() {
        return roleMapper;
    }

    public List<RoleVo> queryRolesByUserName(String userName) {

        Example example = new Example(UserRole.class);

        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userName", userName);

        List<UserRole> userRoles = this.userRoleMapper.selectByExample(example);

        List<Role> roles = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList())
                .stream().map(this::selectById).collect(Collectors.toList());

        return JSON.parseObject(JSON.toJSONString(roles), new TypeReference<List<RoleVo>>(){}.getType());
    }

    public PageInfo<RoleVo> roleVoPageInfo(RoleVo roleVo){

        Page<RoleVo> page = PageHelper.startPage(roleVo.getPage(), roleVo.getPageSize());

        Example example = new Example(Role.class);

        example.orderBy("rawUpdateTime").desc();

        Example.Criteria criteria = example.createCriteria();

        if (!Strings.isNullOrEmpty(roleVo.getName())){

            criteria.andLike("name","%" + roleVo.getName() + "%");

        }

        this.list(example);

        return new PageInfo<>(page);
    }

    //过滤所选择用户已经被选了的角色
    public List<RoleVo> roleVoListForOther(UserRoleVo userRoleVo){

        Example example = new Example(Role.class);

        Example.Criteria criteria = example.createCriteria();

        if (!Strings.isNullOrEmpty(String.valueOf(userRoleVo.getUserId()))){

            List<Long> ids = this.userRoleService.userRoleVoList(userRoleVo)
                    .stream().map(UserRoleVo::getRoleId).collect(Collectors.toList());

            if (!Iterables.isEmpty(ids))

                criteria.andNotIn("id", ids);

        }

        List<Role> roles = this.list(example);

        return JSON.parseObject(JSON.toJSONString(roles), new TypeReference<List<RoleVo>>(){}.getType());
    }

}
