package com.weltec.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: THIS IS ORDER DETAIL
 * Author: Liu
 * Date: 2019-05-05 13:30
 */
@Data
@Entity
@Table(name = "detail")
public class Detail {

    @Id
    private String detailId;
    private String prodId;
    private String prodName;
    private Double prodPrice;
    @Column(name = "prod_quantity")
    private Integer prodQuantity;
    @Column(name = "prod_img")
    private String prodImg;
    private String username;
    private String userID;
    private Integer status;// 0 as unpaid 1 as paid


}
