package com.cqns.demo.web.service.baseservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.dao.entity.Role;
import com.cqns.demo.dao.entity.UserRole;
import com.cqns.demo.dao.mapper.RoleMapper;
import com.cqns.demo.dao.repository.RoleRepository;
import com.cqns.demo.dao.repository.UserRoleRepository;
import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.web.service.AbstractCommonService;
import com.cqns.demo.web.vo.RoleVo;
import com.cqns.demo.web.vo.UserRoleVo;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Service
public class RoleService extends AbstractCommonService<Role> {
    private static Logger logger = LoggerFactory.getLogger(RoleService.class);
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private UserRoleService userRoleService;

    public List<RoleVo> queryRolesByUserName(String userName) {


        List<UserRole> userRoles = this.userRoleRepository.findByUserName(userName);

        List<Role> roles = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList())
                .stream().map(this.roleMapper::selectByPrimaryKey).collect(Collectors.toList());

        return JSON.parseObject(JSON.toJSONString(roles), new TypeReference<List<RoleVo>>(){}.getType());
    }

    public List<RoleVo> roleVoList() {

        List<Role> roles = this.roleRepository.findAll();

        return JSON.parseObject(JSON.toJSONString(roles), new TypeReference<List<RoleVo>>(){}.getType());
    }

    public Page<RoleVo> roleVoPageInfo(RoleVo roleVo){

        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(roleVo.getName())){

                predicates.add(criteriaBuilder.like(root.get("name"),"%" + roleVo.getName() + "%"));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        Pageable pageable = PageRequest.of(roleVo.getPage(), roleVo.getPageSize(), Sort.Direction.DESC, "rawUpdateTime");

        Page<RoleVo> page = this.roleRepository.findAll(specification,pageable);

        return page;
    }

    @Override
    protected BaseRepository<Role> JpaRepository() {
        return roleRepository;
    }
    /**
     * 过滤所选择用户已经被选了的角色
     */
    public List<RoleVo> roleVoListForOther(UserRoleVo userRoleVo){

        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(String.valueOf(userRoleVo.getUserId()))){

                List<Long> ids = this.userRoleService.userRoleVoList(userRoleVo)
                        .stream().map(UserRoleVo::getRoleId).collect(Collectors.toList());

                if (!Iterables.isEmpty(ids)) {

                    predicates.add(criteriaBuilder.not(root.get("id").in(ids)));

                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        List<Role> roles = this.roleRepository.findAll(specification);

        return JSON.parseObject(JSON.toJSONString(roles), new TypeReference<List<RoleVo>>(){}.getType());

    }

    public List<RoleVo> searchRoles(List<Long> ids) {

        List<Role> roles = this.roleRepository.findByIdIn(ids);

        return JSON.parseObject(JSON.toJSONString(roles), new TypeReference<List<RoleVo>>(){}.getType());
    }
}
