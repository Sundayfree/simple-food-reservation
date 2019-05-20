package com.weltec.service;

import com.weltec.pojo.Detail;
import com.weltec.pojo.Product;
import com.weltec.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-05 13:42
 */
public interface IDetailService {
    Page<Detail> findAll(Pageable pageable);
    List<Detail> getProductImg(List<Detail> content);
    void creatOrder(User user, Product product);
    Detail findByDetailId(String Id);
}
