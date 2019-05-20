package com.weltec.sellerController;

import com.weltec.pojo.Detail;
import com.weltec.pojo.User;
import com.weltec.service.IDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-05 14:16
 */
@Controller
public class DetailController {

    @Autowired
    IDetailService detailService;
    @GetMapping("/orders")
    public  String findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "size", defaultValue = "5") Integer size,Model model){

        PageRequest request = PageRequest.of (page - 1, size);
        Page<Detail> all = detailService.findAll (request);
        List<Detail> productImg = detailService.getProductImg (all.getContent ());
        List<Integer> index= null;
        if(all.getTotalPages ()>=0){
            index = IntStream.rangeClosed (1, all.getTotalPages()).boxed ().collect (Collectors.toList ());
        }
        model.addAttribute ("pageInfo",all);
        model.addAttribute ("details",productImg);
        model.addAttribute ("currentPage",page);
        model.addAttribute ("size",size);
        model.addAttribute ("totalPage",index);
        return "admin/orders";
    }
    @GetMapping("/orders/detail")
    public  String getOrderDetail(@RequestParam(name="detailId") String detailId,Model model){

        Detail orderDetail = detailService.findByDetailId (detailId);
        model.addAttribute ("orderDetail",orderDetail);

        return "admin/detail";
    }
}
