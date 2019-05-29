package com.cqns.demo.web.service.baseservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.dao.entity.User;
import com.cqns.demo.dao.repository.UserRepository;
import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.web.service.AbstractCommonService;
import com.cqns.demo.web.vo.RoleVo;
import com.cqns.demo.web.vo.UserVo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class UserService extends AbstractCommonService<User> {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Resource
    private RoleService roleService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    public List<UserVo> userVoList(UserVo userVo){


        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(userVo.getDisplayName())){

                predicates.add(criteriaBuilder.like(root.get("displayName"), "%" + userVo.getDisplayName() + "%"));

            }
            if (!Strings.isNullOrEmpty(userVo.getUsername())){

                predicates.add(criteriaBuilder.equal(root.get("username"), userVo.getUsername()));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        List<User> users = this.userRepository.findAll(specification);

        return JSON.parseObject(JSON.toJSONString(users), new TypeReference<List<UserVo>>(){}.getType());

    }

    public Page<UserVo> userVoPageInfo(UserVo userVo){

        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(userVo.getDisplayName())){

                predicates.add(criteriaBuilder.like(root.get("displayName"),"%" + userVo.getDisplayName() + "%"));

            }

            if (!Strings.isNullOrEmpty(userVo.getUsername())){

                predicates.add(criteriaBuilder.like(root.get("username"),"%" + userVo.getUsername() + "%"));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        Pageable pageable = new PageRequest(userVo.getPage(), userVo.getPageSize(), Sort.Direction.DESC, "rawUpdateTime");

        Page<UserVo> page = this.userRepository.findAll(specification,pageable);

        return page;
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

        User user = this.userRepository.findByUsername(userName);

        UserVo userVO = new UserVo();

        BeanUtils.copyProperties(user, userVO);

        return userVO;

    }

    @Override
    protected BaseRepository<User> JpaRepository() {
        return userRepository;
    }

    public Boolean register(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return this.insert(user);

    }

    public Boolean updateUser(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return this.updateById(user);

    }
}
