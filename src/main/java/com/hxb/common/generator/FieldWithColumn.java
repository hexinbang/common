package com.hxb.common.generator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hexinbang@foxmail.com
 * @date 2019/10/10 21:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldWithColumn {

    private String fieldName;

    private String columnName;

    private String jdbcType;

}
