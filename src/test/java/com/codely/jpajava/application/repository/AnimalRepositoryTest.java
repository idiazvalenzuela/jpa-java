package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Animal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    public void saveOneElementTest() {
        var oneAnimal = new Animal();
        oneAnimal.setName("cow");

        animalRepository.save(oneAnimal);

        var animalsInDb = animalRepository.findAll();

        assertThat(animalsInDb).hasSize(1);
        assertThat(animalsInDb).extracting("id", "name").contains(tuple(1, "cow"));

    }

    @Test
    public void saveAutoincrementTest() {
        var animal = new Animal();
        animal.setName("cow");

        assertThat(animal.getId()).isNull();
        assertThat(animal.getName()).isEqualTo("cow");

        var savedAnimal = animalRepository.save(animal);

        assertThat(animal.getId()).isNotNull();
        assertThat(animal.getId()).isEqualTo(1);
        assertThat(animal.getName()).isEqualTo("cow");

        assertThat(savedAnimal).isEqualTo(animal);

        var animalsInDb = animalRepository.findAll();

        assertThat(animalsInDb).hasSize(1);
        assertThat(animalsInDb).contains(animal);

    }

    @Test
    public void saveSeveralElementsTest() {
        var first = new Animal();
        first.setName("cow");
        var second = new Animal();
        second.setName("horse");
        var third = new Animal();
        third.setName("sheep");

        animalRepository.save(first);
        animalRepository.save(second);
        animalRepository.save(third);

        var animalsInDb = animalRepository.findAll();

        assertThat(animalsInDb).hasSize(3);
        assertThat(animalsInDb).extracting("id", "name").contains(tuple(1, "cow"), tuple(2, "horse"), tuple(3, "sheep"));

    }


}
