package com.cqns.demo.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.baseMap.MyBaseMapper;
import com.cqns.demo.dao.entity.RoleResource;
import com.cqns.demo.dao.mapper.RoleResourceMapper;
import com.cqns.demo.web.vo.RoleResourceVo;
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

@Service
public class RoleResourceService extends AbstractCommonService<RoleResource> {
    private static Logger logger = LoggerFactory.getLogger(RoleResourceService.class);
    @Resource
    private RoleResourceMapper roleResourceMapper;
    @Override
    protected MyBaseMapper<RoleResource> mapper() {
        return this.roleResourceMapper;
    }

    public List<RoleResourceVo> roleResourceVos(RoleResourceVo roleResourceVo){

        Example example = new Example(RoleResource.class);

        example.orderBy("rawUpdateTime").desc();

        Example.Criteria criteria = example.createCriteria();

        if (!Strings.isNullOrEmpty(roleResourceVo.getResourceName())){

            criteria.andLike("resourceName","%" + roleResourceVo.getResourceName() + "%");

        }

        if (!Strings.isNullOrEmpty(String.valueOf(roleResourceVo.getRoleId()))){

            criteria.andEqualTo("roleId", roleResourceVo.getRoleId());

        }

        if (!Strings.isNullOrEmpty(String.valueOf(roleResourceVo.getResourceType()))){

            criteria.andEqualTo("resourceType", roleResourceVo.getResourceType());

        }


        List<RoleResource> roleResources = this.list(example);

        return JSON.parseObject(JSON.toJSONString(roleResources), new TypeReference<List<RoleResourceVo>>(){}.getType());
    }

    public PageInfo<RoleResourceVo> roleResourceVoPageInfo(RoleResourceVo roleResourceVo){

        Page<RoleResourceVo> page = PageHelper.startPage(roleResourceVo.getPage(), roleResourceVo.getPageSize());

        Example example = new Example(RoleResource.class);

        example.orderBy("rawUpdateTime").desc();

        Example.Criteria criteria = example.createCriteria();

        if (!Strings.isNullOrEmpty(roleResourceVo.getRoleName())){

            criteria.andLike("roleName","%" + roleResourceVo.getRoleName() + "%");

        }

        this.list(example);

        return new PageInfo<>(page);
    }

    public boolean updateRoleResource(RoleResourceVo roleResourceVo){


        Example example = new Example(RoleResource.class);

        Example.Criteria criteria = example.createCriteria();

        if (!Strings.isNullOrEmpty(String.valueOf(roleResourceVo.getRoleId()))){

            criteria.andEqualTo("roleId", roleResourceVo.getRoleId());

        }

        return this.updateByExampleSelective(roleResourceVo,example);

    }

}
