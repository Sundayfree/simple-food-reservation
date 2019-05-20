package com.weltec.repository;

import com.weltec.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-03 19:32
 */
public interface IProductRepository  extends JpaRepository<Product,String> {
}
