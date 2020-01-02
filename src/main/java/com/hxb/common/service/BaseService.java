package com.hxb.common.service;

import com.hxb.common.query.BaseQuery;
import com.hxb.common.result.PagingResult;

import java.util.List;

/**
 * @author hexinbang@foxmail.com
 * @date 2019/9/9 16:36
 */
public interface BaseService<T,Q extends BaseQuery> {

    T queryById(Q var1);

    T queryById(Integer var1);

    PagingResult<T> queryPage(Q var1);

    PagingResult<T> queryList(Q var1);

    int count(Q var1);

    void deleteById(Integer var1);

    void deleteByIds(List<Integer> var1);

    void save(T var1);

    int saveAndReturnId(T var1);

    void batchSave(List<T> var1);

    void update(T var1);

    void batchUpdate(T var1, List<Integer> var2);
}
