package com.weltec.service;

import com.weltec.VO.ProductVO;
import com.weltec.dto.ProductDto;
import com.weltec.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-03 23:52
 */
public interface IProductService {
    List<Product> getPageImg(List<Product> content);
    void save(Product prod);
    Page<Product> findAll(Pageable pageable);
    Product onSell(String productId);
    Product offSell(String productId);
    boolean validate(ProductDto productDto);
    String upload(MultipartFile file);
    void deleteById(String id);
    Product findById(String id);
    List<ProductVO> findAll();

}
