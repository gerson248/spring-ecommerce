package com.unmsm.springboot.backend.ecommerce.models.dao;

import com.unmsm.springboot.backend.ecommerce.models.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IUsersDao extends JpaRepository<Users, Long> {

    public Users findByEmail(String email);

}
