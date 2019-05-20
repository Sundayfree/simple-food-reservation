package com.weltec.sellerController;


import com.weltec.exception.UserException;
import com.weltec.pojo.User;
import com.weltec.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Description:
 * Author: Liu
 * Date: 2019-05-02 16:29
 */
@Controller
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/manage/login")
    public String login(User user, Model model, HttpSession session){
        try{
            if(userService.checkUser (user.getUsername (),user.getPassword ())){
                User u = userService.findByName (user.getUsername ());
                if(u.getUserType ()==1){
                    session.setAttribute ("currentUser",u);
                    return "redirect:/dashboard";
                }else{
                    model.addAttribute ("errorMsg","You are not admin.");
                    return "login";
                }

            }
        }catch(UserException e){
            model.addAttribute ("errorMsg",e.getMessage ());
        }
        return "login";
    }
    @GetMapping("/manage/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession ();
        session.removeAttribute ("currentUser");
        return "redirect:/ ";
    }
    @GetMapping("/users")
    public String findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "8") Integer size,Model model){
        PageRequest request = PageRequest.of (page - 1, size);
        Page<User> all = userService.findAll (request);

        List<Integer> pages= null;
        if(all.getTotalPages ()>=0){
            pages = IntStream.rangeClosed (1, all.getTotalPages()).boxed ().collect (Collectors.toList ());
        }
        model.addAttribute ("pageInfo",all);
        model.addAttribute ("users",all.getContent ());
        model.addAttribute ("currentPage",page);
        model.addAttribute ("size",size);
        model.addAttribute ("totalPage",pages);
        return "admin/userList";
    }

}
