package com.hxb.common.query;

import java.util.List;

/**
 * @author hexinbang@foxmail.com
 * @date 2019/9/8 15:41
 */
public class BaseQuery extends PageQuery{

    protected Integer id;

    protected List<Integer> ids;

    protected Integer roleId = 4;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
