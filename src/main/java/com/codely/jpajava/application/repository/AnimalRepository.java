package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Animal;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableJpaRepositories
public interface AnimalRepository extends CrudRepository<Animal, Integer> {
}
