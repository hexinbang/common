package com.hxb.common.dao;

import com.hxb.common.query.BaseQuery;
import com.hxb.common.result.PagingResult;
import java.util.List;


/**
 * @author hexinbang@foxmail.com
 * @date 2019/9/8 15:40
 */

public interface BaseDao<T,Q extends BaseQuery> {

    PagingResult<T> queryPage(Q var1);

    List<T>queryList(Q var1);

    List<T>queryByIds(Q var1);

    List<T>queryByIds(List<Integer> var1);

    T queryById(Q var1);

    T queryById(int var1);

    void save(T var1);

    void batchSave(List<T> var1);

    int saveAndReturnId(T var1);

    void deleteById(Integer var1);

    void deleteByIds(List<Integer> var1);

    void update(T var1);

    int count(Q var1);

    void batchUpdate(T var1, List<Integer> var2);
}
