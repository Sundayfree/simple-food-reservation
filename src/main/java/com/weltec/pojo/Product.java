package com.weltec.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-03 19:14
 */
@Entity
@Table(name = "product")
@Data
@DynamicUpdate
public class Product {

    @Id
    private String proID;
    private String prodName;
    private String prodImg;

    private Double prodPrice;
    private String prodDesc;
    @Column(name="prod_stock")
    private Integer stock;
    @Column(name="prod_createTime")
    private Date createTime;
    @Column(name="prod_updateTime")
    private Date updateTime;
    @Column(name="prod_state")
    private Integer state=1; // 1 as on the market 0 as off the market
}
