package com.cwj.reggie.common;

import lombok.Data;

/**
 * @author cwj
 * @version 1.0.0
 * @date 2022/9/21 16:11
 * @description:
 */
@Data
public class PageS {
    private Integer page;
    private Integer pageSize;
    private Long number;


    private String beginTime;


    private String endTime;


}
