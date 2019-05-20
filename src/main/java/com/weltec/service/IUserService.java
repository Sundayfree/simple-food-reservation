package com.weltec.service;


import com.weltec.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-02 14:36
 */
public interface IUserService {
    void save(User u);

    Page<User> findAll(Pageable pageable);
    User findByName(String name);
   boolean checkUser(String name ,String password);
   User findByID(String Id);
   void sendEmail(String username ,String email);


}
