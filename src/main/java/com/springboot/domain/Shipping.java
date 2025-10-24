package com.springboot.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private String date;



    @OneToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address = new Address();


    }


