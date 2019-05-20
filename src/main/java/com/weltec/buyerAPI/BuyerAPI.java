package com.weltec.buyerAPI;


import com.weltec.VO.ProductVO;
import com.weltec.VO.ResultVO;
import com.weltec.VO.UserVO;
import com.weltec.pojo.Product;
import com.weltec.pojo.User;
import com.weltec.service.IDetailService;
import com.weltec.service.IProductService;
import com.weltec.service.IUserService;
import com.weltec.utils.KeyGenerator;
import com.weltec.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.sound.midi.SoundbankResource;
import java.util.List;


/**
 * Description:
 * Author: Liu
 * Date: 2019-05-09 13:48
 */
@RestController()
public class BuyerAPI {
    @Autowired
    IUserService userservice;
    @Autowired
    IProductService productService;
    @Autowired
    IDetailService detailService;

    @PostMapping("/api/signin")
    public ResultVO login(@RequestBody User u) {
        try {
            if (userservice.checkUser (u.getUsername (), u.getPassword ())) {
                User user = userservice.findByName (u.getUsername ());
                UserVO uVo = new UserVO ();
                BeanUtils.copyProperties (user, uVo);
                return ResultUtils.success (uVo);
            }
        } catch (Exception e) {
            return ResultUtils.error (HttpStatus.BAD_REQUEST.value (), e.getMessage ());
        }
        return ResultUtils.error (HttpStatus.INTERNAL_SERVER_ERROR.value (), "Server sucks.");
    }

    @PostMapping("/api/signup")
    public ResultVO register(@RequestBody User u) {

        User user = new User ();
        BeanUtils.copyProperties (u, user);
        user.setUserID (KeyGenerator.genUniqueKey ());
        user.setUserType (0);

        try {
            userservice.save (user);
            userservice.sendEmail (user.getUsername (), user.getEmail ());
        } catch (Exception e) {
            return ResultUtils.error (HttpStatus.BAD_REQUEST.value (), e.getMessage ());
        }
        return ResultUtils.success ();
    }

    @GetMapping("/api/find")
    public ResultVO findAllProducts() {
        List<ProductVO> all = productService.findAll ();
        return ResultUtils.success (all);
    }

    @PutMapping("/api/buy/{userID}/{proID}")
    public ResultVO buyProduct(@PathVariable("userID") String userID, @PathVariable("proID") String proID) {
        System.out.println ("coming.........");
        System.out.println (userID+"             "+proID);
        User user = userservice.findByID (userID);
        Product product = productService.findById (proID);
        product.setProdImg (product.getProdImg ().substring (product.getProdImg ().lastIndexOf ("/") + 1));
        detailService.creatOrder (user, product);
        return ResultUtils.success ();
    }
}
