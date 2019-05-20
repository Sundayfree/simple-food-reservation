package com.weltec.service.impl;

import com.weltec.VO.ProductVO;
import com.weltec.dto.ProductDto;
import com.weltec.exception.ProductException;
import com.weltec.pojo.Product;
import com.weltec.repository.IProductRepository;
import com.weltec.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-03 23:54
 */
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    IProductRepository productRepository;
    @Value ("${filePath}")
    private String FILEPATH;

    @Override
    public void deleteById(String id) {
        productRepository.deleteById (id);
    }


    @Value ("${fileAbsolutePath}")
    private String FILEABSOLUTEPATH;

    @Override
    public Product findById(String id) {
        Product product = productRepository.findById (id).get ();
        product.setProdImg (FILEPATH+product.getProdImg ());
        return product;
    }

    @Override
    public List<ProductVO> findAll() {
        List<Product> all = productRepository.findAll ().stream ()
                .filter (p -> p.getState () == 1).collect (Collectors.toList ());
        List<ProductVO> list= new ArrayList<> ();
        for (Product p:all) {
            ProductVO productVO =new ProductVO ();
            BeanUtils.copyProperties (p,productVO );
            productVO.setProdImg ("/upload/"+p.getProdImg ());
            list.add (productVO);
        }
        return list;
    }

    @Override
    public List<Product> getPageImg(List<Product> content) {
        return content.stream ().peek (e->e.setProdImg
                (FILEPATH + e.getProdImg ())).collect (Collectors.toList ());
    }

    @Override
    public Product onSell(String productId) {
        Product product = productRepository.findById (productId).get ();
        product.setState (1);
        productRepository.save (product);
        return product;
    }

    @Override
    public Product offSell(String productId) {
        Product product = productRepository.findById (productId).get ();
        product.setState (0);
        productRepository.save (product);
        return product;
    }

    @Override
    public boolean validate(ProductDto productDto) {
        if(StringUtils.isEmpty (productDto.getProdName ().trim ())){
            throw new ProductException ("you have to enter name");
        }
        if(StringUtils.isEmpty (productDto.getProdPrice ())){
            throw new ProductException ("you have to enter price");
        }
        if(StringUtils.isEmpty (productDto.getStock ())){
            throw new ProductException ("you have to enter stock");
        }
        if(StringUtils.isEmpty (productDto.getProdDesc ().trim ())){
            throw new ProductException ("you have to enter description");
        }

        if(Double.isNaN (productDto.getProdPrice ())||Double.isNaN (productDto.getStock ())){
            throw new ProductException ("You have to enter number.");
        }
        return true;
    }

    @Override
    public String upload(MultipartFile file) {
        if(file.getSize ()>1024*1024){
            throw new ProductException ("File too big, change it.");
        }
        if(file.isEmpty ()){
            throw new ProductException ("Upload fails.");
        }
        String filename = file.getOriginalFilename ();

        File dest = new File (FILEABSOLUTEPATH+filename);

        try {
            file.transferTo (dest);
            return filename;
        } catch (IOException e) {
           throw new ProductException ("Destination not found. ");
        }
    }

    @Override
    public void save(Product prod) {
         productRepository.save (prod);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll (pageable);
    }



}
