package com.weltec.repository;

import com.weltec.pojo.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-05 13:35
 */
public interface IDetailRepository extends JpaRepository<Detail,String> {
}
