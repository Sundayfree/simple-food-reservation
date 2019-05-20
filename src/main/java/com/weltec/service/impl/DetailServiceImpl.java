package com.weltec.service.impl;

import com.weltec.pojo.Detail;
import com.weltec.pojo.Product;
import com.weltec.pojo.User;
import com.weltec.repository.IDetailRepository;
import com.weltec.service.IDetailService;
import com.weltec.utils.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-05 13:42
 */
@Service
public class DetailServiceImpl implements IDetailService {
    @Autowired
    WebSoketService webSoketService;
    @Autowired
    IDetailRepository detailRepository;
    @Value("${filePath}")
    private String FILEPATH;
    @Override
    public Page<Detail> findAll(Pageable pageable) {
        return detailRepository.findAll (pageable);
    }

    @Override
    public List<Detail> getProductImg(List<Detail> content) {
        return content.stream ().peek (e->e.setProdImg
                (FILEPATH + e.getProdImg ())).collect (Collectors.toList ());
    }

    @Override
    public void creatOrder(User user, Product product) {
        System.out.println ("comonhdsddsd");
        Detail order = new Detail ();
        order.setProdName (product.getProdName ());
        order.setProdImg (product.getProdImg ());
        order.setDetailId (KeyGenerator.genUniqueKey ());
        order.setProdId (product.getProID ());
        order.setProdPrice (product.getProdPrice ());
        order.setProdQuantity (product.getStock ());
        order.setUserID (user.getUserID ());
        order.setUsername (user.getUsername ());
        order.setStatus (1);
        detailRepository.save (order);
        webSoketService.sendMessage ("You have new order...");

    }

    @Override
    public Detail findByDetailId(String Id) {
        Detail detail = detailRepository.findById (Id).get ();
        detail.setProdImg (FILEPATH+detail.getProdImg ());
        return detail;
    }
}
