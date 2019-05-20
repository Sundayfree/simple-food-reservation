package com.weltec.utils;


import com.weltec.VO.ResultVO;
import org.springframework.http.HttpStatus;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-09 13:58
 */
public class ResultUtils {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("success");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        resultVO.setData (null);
        return resultVO;
    }

}
