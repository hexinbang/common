package com.hxb.common.service.impl;

import com.hxb.common.dao.BaseDao;
import com.hxb.common.query.BaseQuery;
import com.hxb.common.result.PagingResult;
import com.hxb.common.service.BaseService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author hexinbang@foxmail.com
 * @date 2019/9/9 16:36
 */
public  class BaseServiceImpl<T,Q extends BaseQuery> implements BaseService<T,Q> {
    @Autowired
    private BaseDao<T,Q> baseDao;

    public T queryById(@NonNull Q query) {
        if(query.getId()==null){
            throw new NullPointerException("id");
        }else{
            return this.baseDao.queryById(query);
        }
    }

    public T queryById(@NonNull Integer id) {
        return this.baseDao.queryById(id);
    }

    public PagingResult<T> queryPage(@NonNull Q query) {
        return this.baseDao.queryPage(query);
    }

    public PagingResult<T> queryList(@NonNull Q query) {
        List<T>data=this.baseDao.queryList(query);
        int total=data.size();
        PagingResult<T>result=new PagingResult<T>(data,total);
        return result;
    }

    public int count(@NonNull Q query) {
        return this.baseDao.count(query);
    }

    public void deleteById(@NonNull Integer id) {
        this.baseDao.deleteById(id);
    }

    public void deleteByIds(@NonNull List<Integer> ids) {
        this.baseDao.deleteByIds(ids);
    }

    public void save(@NonNull T record) {
        this.baseDao.save(record);
    }

    public int saveAndReturnId(@NonNull T record) {
        return this.baseDao.saveAndReturnId(record);
    }

    public void batchSave(@NonNull List<T> records) {
        this.baseDao.batchSave(records);
    }

    public void update(@NonNull T record) {
        this.baseDao.update(record);
    }

    public void batchUpdate(@NonNull T record, @NonNull List<Integer> ids) {
        this.baseDao.batchUpdate(record,ids);
    }
}
