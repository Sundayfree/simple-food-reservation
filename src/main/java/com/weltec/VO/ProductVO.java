package com.weltec.VO;

import lombok.Data;

import javax.persistence.Column;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-11 09:50
 */
@Data
public class ProductVO {
    private String proID;
    private String prodName;
    private String prodImg;
    private String prodDesc;
    private Double prodPrice;

}
