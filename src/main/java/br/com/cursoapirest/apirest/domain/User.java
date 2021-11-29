package br.com.cursoapirest.apirest.domain;

import lombok.*;
import org.hibernate.loader.plan.spi.BidirectionalEntityReference;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;



}
