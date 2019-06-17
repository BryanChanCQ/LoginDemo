package com.cqns.demo.web.service.baseservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.Dictionary;
import com.cqns.demo.dao.repository.DictionaryRepository;
import com.cqns.demo.web.service.AbstractCommonService;
import com.cqns.demo.web.vo.DictionaryVo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @Author BryanChan
 * @Date 2019-06-03 10:35
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Service
public class DictionaryService extends AbstractCommonService<Dictionary> {

    @Resource
    private DictionaryRepository dictionaryRepository;

    @Override
    protected BaseRepository<Dictionary> JpaRepository() {
        return this.dictionaryRepository;
    }

    public Page<DictionaryVo> dictionaryVoPageInfo(DictionaryVo dictionaryVo){

        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(dictionaryVo.getName())){

                predicates.add(criteriaBuilder.like(root.get("name"),"%" + dictionaryVo.getName() + "%"));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        Pageable pageable = PageRequest.of(dictionaryVo.getPage(), dictionaryVo.getPageSize(), Sort.Direction.DESC, "rawUpdateTime");

        Page<DictionaryVo> page = this.dictionaryRepository.findAll(specification,pageable);

        return page;
    }

    public List<DictionaryVo> parentDictionaryVoList(DictionaryVo dictionaryVo){


        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(String.valueOf(dictionaryVo.getParentId()))){

                predicates.add(criteriaBuilder.equal(root.get("parentId"), dictionaryVo.getParentId()));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        List<Dictionary> dictionaries = this.dictionaryRepository.findAll(specification);

        return JSON.parseObject(JSON.toJSONString(dictionaries), new TypeReference<List<DictionaryVo>>(){}.getType());

    }
}
