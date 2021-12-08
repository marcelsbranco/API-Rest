package br.com.cursoapirest.apirest.services.impl;

import br.com.cursoapirest.apirest.domain.User;
import br.com.cursoapirest.apirest.domain.userDTO.UserDTO;
import br.com.cursoapirest.apirest.repositories.UserRepository;
import br.com.cursoapirest.apirest.services.exceptions.DataIntegratyViolationException;
import br.com.cursoapirest.apirest.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    public static final int ID = 1;
    public static final String NAME = "Marjorie";
    public static final String EMAIL = "marjorie@email.com";
    public static final String PASSWORD = "123";
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void deveRetornarUsuarioPorId() {
        when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());

    }

    @Test
    void deveRetornarExcecaoDoMetodoFindById(){
        when(repository.findById(ID)).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        try {
            service.findById(ID);
        }catch (Exception exception){
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Objeto não encontrado", exception.getMessage());
        }
    }


    @Test
    void deveRetornarListaDeusuarios() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(0).getClass());
    }

    @Test
    void deveRetornarUsuarioCriado() {
        when(repository.save(Mockito.any())).thenReturn(user);

       User response = service.create(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(1, response.getId());
    }

    @Test
    void deveRetornarException(){
        when(repository.findByEmail(Mockito.any())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2);
            service.create(userDTO);
        }catch (Exception exception){
            assertEquals(DataIntegratyViolationException.class, exception.getClass());
            assertEquals("Email já cadastrado", exception.getMessage());
        }
    }

    @Test
    void deveRetornarUsuarioAtualizado() {
        when(repository.save(Mockito.any())).thenReturn(user);

        User response = service.update(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(1, response.getId());
    }

    @Test
    void deveRetornarExceptionAoAtualizar(){
        when(repository.findByEmail(Mockito.any())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2);
            service.update(userDTO);
        }catch (Exception exception){
            assertEquals(DataIntegratyViolationException.class, exception.getClass());
            assertEquals("Email já cadastrado", exception.getMessage());
        }
    }

    @Test
    void deveDeletarUsuario() {
        when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(1);
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deveRetornarErroAoDeletar(){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
        try {
            service.delete(1);
        }catch (Exception exception){
            assertEquals(ObjectNotFoundException.class, exception.getClass()]);
        }
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID,NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));

    }

}