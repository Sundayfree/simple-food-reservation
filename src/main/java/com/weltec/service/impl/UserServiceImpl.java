package com.weltec.service.impl;


import com.google.common.base.Objects;
import com.weltec.exception.UserException;
import com.weltec.pojo.User;
import com.weltec.repository.IUserRepository;
import com.weltec.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;



/**
 * Description:
 * Author: Liu
 * Date: 2019-05-02 14:36
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Value ("${spring.mail.username}")
    private String from;

    @Override
    public void save(User u) {
        if(Objects.equal(null,u)){
            throw new UserException ("U have to enter info..");
        }
        userRepository.findAll ().forEach (user->{
            if(Objects.equal (user.getUsername (),u.getUsername ())){
                throw new UserException ("User already Exists..");
            }
        });
        userRepository.save (u);
    }

    @Override
    public boolean checkUser(String name ,String password) {
        if(StringUtils.isEmpty (name)||StringUtils.isEmpty (password)){
            throw  new UserException ("You have to enter name or password.");
        }
        List<User> list = userRepository.findAll ();
        for (User user :list) {
            if (user.getUsername ().equals (name) && user.getPassword ().equals (password)) {

                return true;
            }
            if (user.getUsername ().equals (name) && !user.getPassword ().equals (password)) {
                throw new UserException ("Username or password not correct.");
            }
        }
        throw new UserException ("User has not registered.");
    }

    @Override
    public User findByID(String Id) {
        return userRepository.findByUserID (Id);
    }

    @Override
    public void sendEmail(String usename,String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("FOODIE PARADISE");
        message.setText("Dear "+usename+"   \n      Congratulation!!! Welcome to join our family!!!!!");
        mailSender.send(message);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll (pageable);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByUsername (name);
    }


}
