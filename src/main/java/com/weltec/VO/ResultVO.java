package com.weltec.VO;

import lombok.Data;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-09 14:03
 */
@Data
public class ResultVO<T> {
    private Integer code;

    private String msg;
    private T data;
}
