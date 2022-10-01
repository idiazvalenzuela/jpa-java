package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    @BeforeEach
    public void setUp() {
        animalRepository.deleteAll();
    }

    @Test
    public void saveOneElementTest() {
        var oneAnimal = new Animal();
        oneAnimal.setName("cow");

        animalRepository.save(oneAnimal);

        var animalsInDb = animalRepository.findAll();

        assertThat(animalsInDb).hasSize(1);
        assertThat(animalsInDb).contains(oneAnimal);

    }

    @Test
    public void saveAutoincrementTest() {
        var animal = new Animal();
        animal.setName("cow");

        assertThat(animal.getId()).isNull();
        assertThat(animal.getName()).isEqualTo("cow");

        var savedAnimal = animalRepository.save(animal);

        assertThat(animal.getId()).isNotNull();
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
        assertThat(animalsInDb).containsExactly(first, second, third);

    }

    @Test
    public void findAnimalByName() {
        var cow = new Animal();
        cow.setName("cow");
        var horse = new Animal();
        horse.setName("horse");
        var sheep = new Animal();
        sheep.setName("sheep");

        animalRepository.save(cow);
        animalRepository.save(horse);
        animalRepository.save(sheep);

        var found = animalRepository.findAnimalByName("horse");

        assertThat(found).isEqualTo(horse);

    }

    @Test
    public void findAnimalByNameNotFound() {
        var cow = new Animal();
        cow.setName("cow");
        var horse = new Animal();
        horse.setName("horse");
        var sheep = new Animal();
        sheep.setName("sheep");

        animalRepository.save(cow);
        animalRepository.save(horse);
        animalRepository.save(sheep);

        var notFound = animalRepository.findAnimalByName("goat");

        assertThat(notFound).isNull();

    }

    @Test
    public void findAnimalsByName() {
        var cow = new Animal();
        cow.setName("cow");
        var anotherCow = new Animal();
        anotherCow.setName("cow");
        var yetAnotherCow = new Animal();
        yetAnotherCow.setName("cow");

        animalRepository.save(cow);
        animalRepository.save(anotherCow);
        animalRepository.save(yetAnotherCow);

        assertThatExceptionOfType(IncorrectResultSizeDataAccessException.class).
                isThrownBy(() -> animalRepository.findAnimalByName("cow"));

        var found = animalRepository.findFirstByName("cow");

        assertThat(found).isEqualTo(cow);

        var listOfCows = animalRepository.findAnimalsByName("cow");

        assertThat(listOfCows).hasSize(3);
        assertThat(listOfCows).containsExactly(cow, anotherCow, yetAnotherCow);

    }


}
