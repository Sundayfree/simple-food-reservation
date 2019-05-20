package com.weltec.repository;

import com.weltec.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-02 13:27
 */
public interface IUserRepository extends JpaRepository<User,String> {
    User findByUsername(String name);
    User findByUserID(String Id);
}
