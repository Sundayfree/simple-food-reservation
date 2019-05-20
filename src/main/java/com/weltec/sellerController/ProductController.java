package com.weltec.sellerController;

import com.weltec.dto.ProductDto;
import com.weltec.exception.ProductException;
import com.weltec.pojo.Product;
import com.weltec.service.IProductService;
import com.weltec.utils.KeyGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.SoundbankResource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-04 01:02
 */
@Controller
public class ProductController {

    @Autowired
    IProductService productService;
    @Value("${fileAbsolutePath}")
    private String FILEABSOLUTEPATH;

    @GetMapping("/products")
    public String findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "5") Integer size,Model model){
        PageRequest request = PageRequest.of (page - 1, size);
        Page<Product> pageInfo = productService.findAll (request);
        List<Product> all =productService.getPageImg (pageInfo.getContent ());
        List<Integer> totalPage= null;
        if(pageInfo.getTotalPages ()>=0){
            totalPage = IntStream.rangeClosed (1, pageInfo.getTotalPages()).boxed ().collect (Collectors.toList ());
        }
        model.addAttribute ("products",all);
        model.addAttribute ("pageInfo",pageInfo);
        model.addAttribute ("currentPage",page);
        model.addAttribute ("size",size);
        model.addAttribute ("totalPage",totalPage);
        return "admin/products";
    }
    @GetMapping("/products/on_sell")
    public String onSell(@RequestParam("proID") String Id)
    {
        Product product = productService.offSell (Id);
        return "forward:/products";
    }
    @GetMapping("/products/off_sell")
    public String offSell(@RequestParam("proID") String Id)
    {
        Product product = productService.onSell (Id);
        return "forward:/products";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam(name="productId")String id){
        productService.deleteById (id);
        return "forward:/products";
    }
    @PostMapping("/add/products")
    public String save(ProductDto productDto,
                       @RequestParam("file") MultipartFile file,
                       Model model){
        try{
            if(productService.validate(productDto)){
                Product prod = new Product ();
                prod.setProID (KeyGenerator.genUniqueKey ());
                prod.setCreateTime (new Date ());
                prod.setUpdateTime (new Date ());
                String fileName = productService.upload (file);
                BeanUtils.copyProperties (productDto,prod);
                prod.setProdImg ( fileName);
                productService.save (prod);

                return "redirect:/products";
            }
        }catch(ProductException e){
            model.addAttribute ("errMsg",e.getMessage ());
            return  "admin/addProd";
        }
        return  "forward:/add/products";
    }

    @GetMapping("/update")
    public String update(@RequestParam(name="productId") String id, Model model){
        Product product = productService.findById (id);
        model.addAttribute ("product",product);
        model.addAttribute ("productId",id);
        return "admin/updateProd";
    }
    @PostMapping("/uppdate/products")
    public String updateProduct(ProductDto productDto,
                                @RequestParam(name = "productId") String id,
                                @RequestParam("file") MultipartFile file)
    {
        System.out.println (id);
        Product product = productService.findById (id);
        String fileName= product.getProdImg ();
        BeanUtils.copyProperties (productDto,product);
        if(file.isEmpty ()){
            File dest =  new File(FILEABSOLUTEPATH+fileName);
            try {
                file.transferTo (dest);
            } catch (IOException e) {
                throw new RuntimeException ("upload fails");
            }
        }else{
            fileName = productService.upload (file);
        }
        product.setProID (id);
        product.setUpdateTime (new Date ());
        product.setProdImg (fileName);
        productService.save (product);
        return "redirect:/products";
    }

}
