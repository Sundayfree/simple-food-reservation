package com.weltec.test;

import com.weltec.pojo.User;


import com.weltec.repository.IDetailRepository;
import com.weltec.repository.IProductRepository;
import com.weltec.repository.IUserRepository;
import com.weltec.service.IProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationApplicationTests {

    @Autowired
    IUserRepository dom;
    @Autowired
    IProductService service;
    @Autowired
    IDetailRepository dom3;

    @Autowired
    IProductRepository dom2;
    @Test
    public void contextLoads() {
        User user = dom.findAll ().get (0);
        System.out.println ("ddddd   "+user.getUsername ());

    }
    @Test public void productTest(){
        dom2.findAll ().get (8).getProdImg ();
        System.out.println ( dom2.findAll ().get (8).getProdImg ()+"   "+dom2.count ());

    }
    @Test public void orderService(){


     System.out.println (    dom3.findAll ().size ());
    }
}
