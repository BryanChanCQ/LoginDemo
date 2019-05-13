package com.cqns.demo.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.baseMap.MyBaseMapper;
import com.cqns.demo.dao.entity.User;
import com.cqns.demo.dao.mapper.UserMapper;
import com.cqns.demo.web.vo.RoleVo;
import com.cqns.demo.web.vo.UserVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService extends AbstractCommonService<User> {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Resource
    private RoleService roleService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Override
    protected MyBaseMapper<User> mapper() {
        return userMapper;
    }

    public List<UserVo> userVoList(UserVo userVo){

        Example example = new Example(User.class);

        example.orderBy("rawUpdateTime").desc();

        Example.Criteria criteria = example.createCriteria();

        if (!Strings.isNullOrEmpty(userVo.getDisplayName())){

            criteria.andLike("displayName","%" + userVo.getDisplayName() + "%");

        }
        if (!Strings.isNullOrEmpty(userVo.getUsername())){

            criteria.andEqualTo("username", userVo.getUsername());

        }

        List<User> users = this.list(example);

        return JSON.parseObject(JSON.toJSONString(users), new TypeReference<List<UserVo>>(){}.getType());
    }

    public PageInfo<UserVo> userVoPageInfo(UserVo userVo){

        Page<UserVo> page = PageHelper.startPage(userVo.getPage(), userVo.getPageSize());

        Example example = new Example(User.class);

        example.orderBy("rawUpdateTime").desc();

        Example.Criteria criteria = example.createCriteria();

        if (!Strings.isNullOrEmpty(userVo.getDisplayName())){

            criteria.andLike("displayName","%" + userVo.getDisplayName() + "%");

        }

        if (!Strings.isNullOrEmpty(userVo.getUsername())){

            criteria.andLike("username","%" + userVo.getUsername() + "%");

        }

        this.list(example);

        return new PageInfo<>(page);
    }

    public UserVo queryUserDetailByName(String userName) {

        Preconditions.checkNotNull(userName, "用户名不能为为空");

        UserVo userVo = this.queryUserByName(userName);

        List<RoleVo> roleVOs = this.roleService.queryRolesByUserName(userName);

        userVo.setRoles(roleVOs);

        return userVo;
    }

    public UserVo queryUserByName(String userName) {

        Preconditions.checkNotNull(userName, "用户名不能为为空");

        User user = new User();

        user.setUsername(userName);

        user = this.userMapper.selectOne(user);

        UserVo userVO = new UserVo();

        BeanUtils.copyProperties(user, userVO);

        return userVO;

    }

    public Boolean register(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return this.add(user);

    }

    public Boolean updateUser(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return this.updateById(user);

    }
}
