package com.hxb.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hexinbang@foxmail.com
 * @date 2019/9/8 16:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingResult<T> {

    private List<T> pagingList;

    private Integer total;
}
