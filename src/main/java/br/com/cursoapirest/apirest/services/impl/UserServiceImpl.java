package br.com.cursoapirest.apirest.services.impl;

import br.com.cursoapirest.apirest.domain.User;
import br.com.cursoapirest.apirest.domain.userDTO.UserDTO;
import br.com.cursoapirest.apirest.repositories.UserRepository;
import br.com.cursoapirest.apirest.services.UserService;
import br.com.cursoapirest.apirest.services.exceptions.DataIntegratyViolationException;
import br.com.cursoapirest.apirest.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public User findById(Integer id) {
        Optional<User> user =  userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();

    }

    @Override
    public User create(UserDTO dto) {
        findByEmail(dto);
        return userRepository.save(mapper.map(dto, User.class));
    }

    private void findByEmail(UserDTO dto){
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if(user.isPresent()){
            throw new DataIntegratyViolationException("Email já cadastrado");
        }
    }
}
