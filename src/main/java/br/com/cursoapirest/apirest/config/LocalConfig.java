package br.com.cursoapirest.apirest.config;

import br.com.cursoapirest.apirest.domain.User;
import br.com.cursoapirest.apirest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void startDB(){
        User u1 = new User(null, "Marcel", "marcel@email", "123");
        User u2 = new User(null, "Simone", "simone@email", "123");
        User u3 = new User(null, "Marjorie", "marjorie@email", "123");

        userRepository.saveAll(List.of(u1, u2, u3));

    }
}
