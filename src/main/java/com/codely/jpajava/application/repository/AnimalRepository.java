package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Animal;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableJpaRepositories
public interface AnimalRepository extends CrudRepository<Animal, Integer> {

    Animal findAnimalByName(String name);

    Animal findFirstByName(String name);

    Animal findTopByName(String name);

    List<Animal> findAnimalsByName(String name);

    List<Animal> findAnimalsByNameIgnoreCase(String name);

}
