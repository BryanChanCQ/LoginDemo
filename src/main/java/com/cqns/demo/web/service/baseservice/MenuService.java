package com.cqns.demo.web.service.baseservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.dao.entity.Menu;
import com.cqns.demo.dao.entity.RoleResource;
import com.cqns.demo.dao.mapper.MenuMapper;
import com.cqns.demo.dao.repository.MenuRepository;
import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.web.service.AbstractCommonService;
import com.cqns.demo.web.vo.MenuVo;
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
import java.util.*;

import java.util.stream.Collectors;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Service
public class MenuService extends AbstractCommonService<Menu> {
    private static Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Resource
    private RoleResourceService roleResourceService;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private MenuRepository menuRepository;

    public List<MenuVo> queryMenuByIds(List<Long> ids) {

        List<Menu> menuList = ids.stream().distinct().map(this.menuMapper::selectByPrimaryKey).collect(Collectors.toList());

        return JSON.parseObject(JSON.toJSONString(menuList), new TypeReference<List<MenuVo>>(){}.getType());
    }

    public Page<MenuVo> menuVoPageInfo(MenuVo menuVo){

        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(menuVo.getName())){

                predicates.add(criteriaBuilder.like(root.get("name"),"%" + menuVo.getName() + "%"));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        Pageable pageable = PageRequest.of(menuVo.getPage(), menuVo.getPageSize(), Sort.Direction.ASC, "parentId");

        Page<MenuVo> page = this.menuRepository.findAll(specification,pageable);


        return page;
    }

    public List<MenuVo> queryMenusByRoleIds(List<Long> ids) {

        List<RoleResourceVo> roleResources = Lists.newArrayList();

        RoleResourceVo roleResourceVO = new RoleResourceVo();

        for(int i = 0; i < ids.size(); ++i) {

            roleResourceVO.setRoleId(ids.get(i));

            roleResourceVO.setResourceType(com.cqns.demo.utils.Resource.MENU.getType());

            roleResources.addAll(this.roleResourceService.roleResourceVos(roleResourceVO));

        }

        List<MenuVo> menus = this.queryMenuByIds(roleResources.stream().map(RoleResource::getResourceId).collect(Collectors.toList()));

        List<MenuVo> menus1 = menus.stream().filter(Objects::nonNull).collect(Collectors.toList());

        menus1.sort(Comparator.comparingLong(MenuVo::getParentId).thenComparing(MenuVo::getRawAddTime));

        return menus1;
    }

    @Override
    protected BaseRepository<Menu> JpaRepository() {
        return menuRepository;
    }

    /**
     * 过滤所选择角色已经被选了的菜单
     */
    public List<MenuVo> menuVoListForOther(RoleResourceVo roleResourceVo){

        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(String.valueOf(roleResourceVo.getRoleId()))){

                List<Long> ids = this.roleResourceService.roleResourceVos(roleResourceVo).
                        stream().map(RoleResourceVo::getResourceId).collect(Collectors.toList());

                if (Objects.nonNull(ids) && !ids.isEmpty()) {

                    predicates.add(criteriaBuilder.not(root.get("id").in(ids)));

                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        List<Menu> menus = this.menuRepository.findAll(specification);

        return JSON.parseObject(JSON.toJSONString(menus), new TypeReference<List<MenuVo>>(){}.getType());
    }
}
