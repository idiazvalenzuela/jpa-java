package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    public void saveOneElementTest() {

        var owner = Owner.builder().id(123).name("Jane Marie").preferredName("Jany").build();

        var savedOwner = ownerRepository.save(owner);

        var ownerInDb = ownerRepository.findAll();

        assertThat(ownerInDb).hasSize(1);
        assertThat(ownerInDb).contains(savedOwner);
    }

    @Test
    public void saveUpsertTest() {

        var owner = Owner.builder().id(123).name("Jane Marie").preferredName("Jany").build();
        var anotherOwner = Owner.builder().id(123).name("John").preferredName("John").build();

        ownerRepository.save(owner);
        ownerRepository.save(anotherOwner);

        var ownerInDb = ownerRepository.findAll();

        assertThat(ownerInDb).hasSize(1);
        assertThat(ownerInDb).contains(anotherOwner);
    }

    @Test
    public void uniqueConstraint() {

        var owner = Owner.builder().id(123).email("mail@mail.com").name("Jane Marie").build();
        var anotherOwner = Owner.builder().id(456).email("mail@mail.com").name("John Smith").build();

        ownerRepository.save(owner);

        var ownerInDb = ownerRepository.findAll();

        assertThat(ownerInDb).hasSize(1);
        assertThat(ownerInDb).contains(owner);

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> ownerRepository.save(anotherOwner));
    }

    @Test
    public void notNullConstraint() {

        var owner = Owner.builder().id(123).email("mail@mail.com").build();
        
        assertThatExceptionOfType(TransactionSystemException.class)
                .isThrownBy(() -> ownerRepository.save(owner));
    }


}
