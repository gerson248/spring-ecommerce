package com.unmsm.springboot.backend.ecommerce.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unmsm.springboot.backend.ecommerce.models.dao.IUsersDao;
import com.unmsm.springboot.backend.ecommerce.models.entity.Users;

@Service
public class UsersService implements IUsersService {

    @Autowired
    private IUsersDao usersDao;

    @Override
    @Transactional(readOnly = true)
    public List<Users> findAll() {
        return (List<Users>) usersDao.findAll();
    }

    @Override
    public Users save(Users users) {
        // @Transactional(readOnly = true) si se coloca est,
        // sirve para que el servicio solo sea de lectura y no de escritura
        return (Users) usersDao.save(users);
    }

    @Override
    @Transactional(readOnly=true)
    public Users findById(Long id) {
        return (Users) usersDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly=true)
    public Users findByEmail(String email) {
        return (Users) usersDao.findByEmail(email);
    }
}
