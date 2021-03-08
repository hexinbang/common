package com.hxb.common.dao.impl;

import com.hxb.common.dao.BaseDao;
import com.hxb.common.query.BaseQuery;
import com.hxb.common.result.PagingResult;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hexinbang@foxmail.com
 * @date 2019/9/9 10:44
 */

public abstract class BaseDaoImpl<T, Q extends BaseQuery> extends SqlSessionDaoSupport implements BaseDao<T, Q> {

    @Override
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public abstract String getNameSpace();

    public BaseDaoImpl() {
    }

    @SneakyThrows
    public PagingResult<T> queryPage(@NonNull Q query) {
        if (query.getRows() == null || query.getStart() == null) {
            throw new Exception("query start and row can't be null");
        } else {
            List<T> data = this.selectList("queryPage", query);
            int total = (Integer) this.selectOne("count", query);
            PagingResult<T> result = new PagingResult(data, total);
            return result;
        }
    }

    public List<T> queryList(@NonNull Q query) {
        return this.selectList("queryList", query);
    }

    public List<T> queryByIds(@NonNull List<Integer> ids) {
        BaseQuery query = new BaseQuery();
        query.setIds(ids);
        return this.selectList("queryByIds", query);
    }

    public T queryById(@NonNull int id) {
        BaseQuery query = new BaseQuery();
        query.setId(id);
        return this.selectOne("queryById", query);
    }

    @SneakyThrows
    public List<T> queryByIds(@NonNull Q query) {
        if (query.getIds() == null) {
            throw new Exception("query ids can't be null");
        } else {
            return this.selectList("queryByIds", query);
        }
    }

    @SneakyThrows
    public T queryById(@NonNull Q query) {
        if (query.getId() == null) {
            throw new Exception("query id can't be null");
        } else {
            return this.selectOne("queryById", query);
        }
    }

    public void save(@NonNull T record) {
        this.insert("save", record);
    }

    public void batchSave(@NonNull List<T> records) {
        if (records.size() == 0) return;
        this.insert("batchSave", records);
    }

    @SneakyThrows
    public int saveAndReturnId(@NonNull T record) {
        if (this.insert("saveAndReturnId", record) > 0) {
            try {
                Field field = record.getClass().getDeclaredField("id");
                field.setAccessible(true);
                return (int) field.get(record);
            } catch (Throwable var1) {
                throw var1;
            }
        } else {
            throw new Exception("save error");
        }
    }

    public void deleteById(@NonNull Integer id) {
        this.delete("deleteById", id);
    }

    @SneakyThrows
    public void deleteByIds(@NonNull List<Integer> ids) {
        if (ids.size() == 0) {
            throw new Exception("delete ids can't be empty");
        } else {
            this.delete("deleteByIds", ids);
        }
    }

    public void update(@NonNull T record) {
        String className = record.getClass().getName();
        String mapperMethodId = "update";
        if (className.endsWith("WithBLOBs")) {
            mapperMethodId = "updateWithBLOBs";
        }
        this.update(mapperMethodId, record);
    }

    public int count(@NonNull Q query) {
        return (Integer) this.selectOne("count", query);
    }

    @SneakyThrows
    public void batchUpdate(@NonNull T data, @NonNull List<Integer> ids) {
        if (ids.size() == 0) {
            throw new Exception("update ids can't be empty");
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("data", data);
            map.put("ids", ids);
            this.update("batchUpdate", map);
        }
    }

    protected T selectOne(@NonNull String id, Object para) {
        return this.getSqlSession().selectOne(this.getNameSpace() + "." + id, para);
    }

    protected List<T> selectList(@NonNull String id, Object para) {
        return this.getSqlSession().selectList(this.getNameSpace() + "." + id, para);
    }

    protected int update(@NonNull String id, Object para) {
        return this.getSqlSession().update(this.getNameSpace() + "." + id, para);
    }

    protected int insert(@NonNull String id, Object para) {
        return this.getSqlSession().insert(this.getNameSpace() + "." + id, para);
    }

    protected int delete(@NonNull String id, Object para) {
        return this.getSqlSession().delete(this.getNameSpace() + "." + id, para);
    }
}
