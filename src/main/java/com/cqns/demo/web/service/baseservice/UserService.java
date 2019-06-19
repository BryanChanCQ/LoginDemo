package com.cqns.demo.web.service.baseservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.dao.entity.User;
import com.cqns.demo.dao.entity.UserRole;
import com.cqns.demo.dao.repository.UserRepository;
import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.web.service.AbstractCommonService;
import com.cqns.demo.web.vo.RoleVo;
import com.cqns.demo.web.vo.UserVo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Service
public class UserService extends AbstractCommonService<User> {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Resource
    private RoleService roleService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRoleService userRoleService;

    public List<UserVo> userVoList(UserVo userVo){


        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(userVo.getDisplayName())){

                predicates.add(criteriaBuilder.like(root.get("displayName"), "%" + userVo.getDisplayName() + "%"));

            }
            if (!Strings.isNullOrEmpty(userVo.getUserName())){

                predicates.add(criteriaBuilder.equal(root.get("username"), userVo.getUserName()));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        List<User> users = this.userRepository.findAll(specification);

        return JSON.parseObject(JSON.toJSONString(users), new TypeReference<List<UserVo>>(){}.getType());

    }

    public Map<String ,Object> userVoPageInfo(UserVo userVo){

    	Map<String ,Object> resultMap = Maps.newHashMap();
    	
        @SuppressWarnings("rawtypes")
		Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(userVo.getDisplayName())){

                predicates.add(criteriaBuilder.like(root.get("displayName"),"%" + userVo.getDisplayName() + "%"));

            }

            if (!Strings.isNullOrEmpty(userVo.getUserName())){

                predicates.add(criteriaBuilder.like(root.get("username"),"%" + userVo.getUserName() + "%"));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        Pageable pageable = PageRequest.of(userVo.getPage(), userVo.getPageSize(), Sort.Direction.DESC, "lastUpdate");

        Page<User> page = this.userRepository.findAll(specification,pageable);

        List<UserVo> usrVo = new ArrayList<>();
        
        for (User usr : page)
        {
        	UserVo uv = new UserVo();
        	
        	BeanUtils.copyProperties(usr, uv);
        	
        	List<UserRole> ur = this.userRoleService.searchUserRole(usr.getId());
        	
        	List<RoleVo> rv = this.roleService.searchRoles(ur.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList()));
        	
        	uv.setRoles(rv);
        	
        	usrVo.add(uv);
        }

        resultMap.put("content" ,usrVo);
        resultMap.put("totalElements" ,page.getTotalElements());
        return resultMap;
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

        User user = this.userRepository.findByUserName(userName);

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
