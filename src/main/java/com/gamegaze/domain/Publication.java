package com.gamegaze.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "publications")
public class Publication {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
