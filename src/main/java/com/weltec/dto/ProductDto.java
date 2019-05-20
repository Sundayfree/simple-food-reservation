package com.weltec.dto;

import lombok.Data;




/**
 * Description:
 * Author: Liu
 * Date: 2019-05-04 14:11
 */
@Data
public class ProductDto {

    private String prodName;
    private String prodImg;
    private Double prodPrice;
    private String prodDesc;
    private Integer stock;
}
