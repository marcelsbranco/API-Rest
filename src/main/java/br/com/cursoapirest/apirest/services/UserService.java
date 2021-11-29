package br.com.cursoapirest.apirest.services;

import br.com.cursoapirest.apirest.domain.User;

public interface UserService {

    User findById(Integer id);
}
