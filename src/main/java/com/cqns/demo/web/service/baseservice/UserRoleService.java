package com.cqns.demo.web.service.baseservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.dao.entity.UserRole;
import com.cqns.demo.dao.repository.UserRoleRepository;
import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.web.service.AbstractCommonService;
import com.cqns.demo.web.vo.UserRoleVo;
import com.google.common.base.Strings;
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
import java.util.Objects;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Service
public class UserRoleService extends AbstractCommonService<UserRole> {
    @Resource
    private UserRoleRepository userRoleRepository;
    @Override
    protected BaseRepository<UserRole> JpaRepository() {
        return userRoleRepository;
    }

    private static Logger logger = LoggerFactory.getLogger(UserRoleService.class);

    public List<UserRoleVo> userRoleVoList(UserRoleVo userRoleVo){

        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(userRoleVo.getRoleName())){

                predicates.add(criteriaBuilder.like(root.get("roleName"),"%" + userRoleVo.getRoleName() + "%"));

            }

            if (!Strings.isNullOrEmpty(userRoleVo.getUserName())){

                predicates.add(criteriaBuilder.equal(root.get("userName"), userRoleVo.getUserName()));

            }

            if (Objects.nonNull(userRoleVo.getUserId())){

                predicates.add(criteriaBuilder.equal(root.get("userId"), userRoleVo.getUserId()));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        List<UserRole> userRoles = this.userRoleRepository.findAll(specification,Sort.by(Sort.Direction.DESC,"rawUpdateTime"));

        return JSON.parseObject(JSON.toJSONString(userRoles), new TypeReference<List<UserRoleVo>>(){}.getType());
    }

    public Page<UserRoleVo> userRoleVoPageInfo(UserRoleVo userRoleVo){

        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(userRoleVo.getUserName())){

                predicates.add(criteriaBuilder.like(root.get("userName"),"%" + userRoleVo.getUserName() + "%"));

            }

            if (!Strings.isNullOrEmpty(userRoleVo.getRoleName())){

                predicates.add(criteriaBuilder.like(root.get("roleName"),"%" + userRoleVo.getRoleName() + "%"));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        Pageable pageable = PageRequest.of(userRoleVo.getPage(), userRoleVo.getPageSize(), Sort.Direction.DESC, "rawUpdateTime");

        Page<UserRoleVo> page = this.userRoleRepository.findAll(specification,pageable);

        return page;
    }

    public boolean updateUserRole(UserRole userRole){

        try {
            Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

                List<Predicate> predicates = Lists.newArrayList();

                if (!Strings.isNullOrEmpty(String.valueOf(userRole.getRoleId()))){

                    predicates.add(criteriaBuilder.equal(root.get("roleId"), userRole.getRoleId() ));

                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

            };

            List<UserRole> userRoles = this.userRoleRepository.findAll(specification);

            userRoles.forEach(userRole1 -> userRole1.setRoleName(userRole.getRoleName()));

            this.userRoleRepository.saveAll(userRoles);

            return true;
        }catch (Exception e) {

            logger.error("Error", e);

            return false;
        }
    }
    
    public List<UserRole> searchUserRole(Long id) {

    	return this.userRoleRepository.findByRoleId(id);

    }
}
