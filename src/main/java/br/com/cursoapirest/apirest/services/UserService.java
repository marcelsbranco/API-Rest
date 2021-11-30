package br.com.cursoapirest.apirest.services;

import br.com.cursoapirest.apirest.domain.User;
import br.com.cursoapirest.apirest.domain.userDTO.UserDTO;

import java.util.List;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO dto);
}
