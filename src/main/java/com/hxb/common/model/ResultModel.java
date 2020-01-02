package com.hxb.common.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author hexb
 * @date 2020/1/2 14:01
 */
@Data
@Builder
public class ResultModel {
    Object data;

    String code;

    String msg;
}
