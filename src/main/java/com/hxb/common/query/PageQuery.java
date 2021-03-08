package com.hxb.common.query;

import lombok.Data;

/**
 * @author hexinbang@foxmail.com
 * @date 2019/9/9 11:57
 */
@Data
public class PageQuery {

    private final Integer maxRows=200;

    protected Integer page;

    protected Integer rows = 20;

    protected Integer start = 0;

    public void setRows(Integer rows){
        this.rows=rows>maxRows?maxRows:rows;
        this.start=(this.page-1)*rows;

    }
    public void setPage(Integer page){
        this.page=page;
        this.start=(this.page-1)*rows;
    }
}
