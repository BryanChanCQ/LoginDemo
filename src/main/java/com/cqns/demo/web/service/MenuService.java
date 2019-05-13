package com.cqns.demo.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.baseMap.MyBaseMapper;
import com.cqns.demo.dao.entity.Menu;
import com.cqns.demo.dao.entity.RoleResource;
import com.cqns.demo.dao.mapper.MenuMapper;
import com.cqns.demo.web.vo.MenuVo;
import com.cqns.demo.web.vo.RoleResourceVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

import java.util.stream.Collectors;

@Service
public class MenuService extends AbstractCommonService<Menu> {
    private static Logger logger = LoggerFactory.getLogger(MenuService.class);
    @Resource
    private RoleResourceService roleResourceService;
    @Resource
    private MenuMapper menuMapper;
    @Override
    protected MyBaseMapper<Menu> mapper() {
        return menuMapper;
    }

    public List<MenuVo> queryMenuByIds(List<Long> ids) {

        List<Menu> menuList = ids.stream().distinct().map(this::selectById).collect(Collectors.toList());

        return JSON.parseObject(JSON.toJSONString(menuList), new TypeReference<List<MenuVo>>(){}.getType());
    }

    public PageInfo<MenuVo> menuVoPageInfo(MenuVo menuVo){

        Page<MenuVo> page = PageHelper.startPage(menuVo.getPage(), menuVo.getPageSize());

        Example example = new Example(Menu.class);

        example.orderBy("parentId").asc();

        Example.Criteria criteria = example.createCriteria();

        if (!Strings.isNullOrEmpty(menuVo.getName())){

            criteria.andLike("name","%" + menuVo.getName() + "%");

        }

        this.list(example);

        return new PageInfo<>(page);
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

    //过滤所选择角色已经被选了的菜单
    public List<MenuVo> menuVoListForOther(RoleResourceVo roleResourceVo){

        Example example = new Example(Menu.class);

        Example.Criteria criteria = example.createCriteria();

        if (!Strings.isNullOrEmpty(String.valueOf(roleResourceVo.getRoleId()))){

            List<Long> ids = this.roleResourceService.roleResourceVos(roleResourceVo).
                    stream().map(RoleResourceVo::getResourceId).collect(Collectors.toList());

            if (Objects.nonNull(ids) && !ids.isEmpty())

                criteria.andNotIn("id", ids);

        }

        List<Menu> menus = this.list(example);

        return JSON.parseObject(JSON.toJSONString(menus), new TypeReference<List<MenuVo>>(){}.getType());
    }
}
