package com.unmsm.springboot.backend.ecommerce.models.services;

import com.unmsm.springboot.backend.ecommerce.models.entity.Users;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUsersService {

    public List<Users> findAll();

    public Users save(Users users);

    public Users findById(Long id);

    public Users findByEmail(String email);

}
