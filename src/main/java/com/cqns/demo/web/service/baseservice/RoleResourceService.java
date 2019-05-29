package com.cqns.demo.web.service.baseservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.dao.entity.RoleResource;
import com.cqns.demo.dao.repository.RoleResourceRepository;
import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.web.service.AbstractCommonService;
import com.cqns.demo.web.vo.RoleResourceVo;
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

@Service
public class RoleResourceService  extends AbstractCommonService<RoleResource> {
    private static Logger logger = LoggerFactory.getLogger(RoleResourceService.class);
    @Resource
    private RoleResourceRepository roleResourceRepository;

    public List<RoleResourceVo> roleResourceVos(RoleResourceVo roleResourceVo){

        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(roleResourceVo.getResourceName())){

                predicates.add(criteriaBuilder.like(root.get("resourceName"),"%" + roleResourceVo.getResourceName() + "%"));

            }

            if (!Strings.isNullOrEmpty(String.valueOf(roleResourceVo.getRoleId()))){

                predicates.add(criteriaBuilder.equal(root.get("roleId"), roleResourceVo.getRoleId()));

            }

            if (Objects.nonNull(roleResourceVo.getResourceType())){

                predicates.add(criteriaBuilder.equal(root.get("resourceType"), roleResourceVo.getResourceType()));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        List<RoleResource> roleResources = this.roleResourceRepository.findAll(specification);

        return JSON.parseObject(JSON.toJSONString(roleResources), new TypeReference<List<RoleResourceVo>>(){}.getType());
    }

    public Page<RoleResourceVo> roleResourceVoPageInfo(RoleResourceVo roleResourceVo){

        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(roleResourceVo.getRoleName())){

                predicates.add(criteriaBuilder.like(root.get("roleName"), "%" + roleResourceVo.getRoleName() + "%"));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        Pageable pageable = new PageRequest(roleResourceVo.getPage(), roleResourceVo.getPageSize(), Sort.Direction.DESC, "rawUpdateTime");

        Page<RoleResourceVo> page = this.roleResourceRepository.findAll(specification,pageable);

        return page;
    }

    @Override
    protected BaseRepository<RoleResource> JpaRepository() {
        return roleResourceRepository;
    }


    public boolean updateRoleResource(RoleResource roleResource){

        try {
            Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

                List<Predicate> predicates = Lists.newArrayList();

                if (!Strings.isNullOrEmpty(String.valueOf(roleResource.getRoleId()))){

                    predicates.add(criteriaBuilder.equal(root.get("roleId"), roleResource.getRoleId() ));

                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

            };

            List<RoleResource> roleResources = this.roleResourceRepository.findAll(specification);

            roleResources.forEach(roleResource1 -> roleResource1.setRoleName(roleResource.getRoleName()));

            this.roleResourceRepository.saveAll(roleResources);

            return true;

        }catch (Exception e) {

            logger.error("Error", e);

            return false;
        }


    }

}
