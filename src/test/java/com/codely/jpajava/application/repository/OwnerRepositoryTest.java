package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Owner;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @BeforeEach
    public void setUp() {
        ownerRepository.deleteAll();
    }

    @Test
    public void saveOneElementTest() {

        var owner = Owner.builder()
                .id(123).name("Jane Marie")
                .preferredName("Jany")
                .build();

        var savedOwner = ownerRepository.save(owner);

        var ownerInDb = ownerRepository.findAll();

        assertThat(ownerInDb).hasSize(1);
        assertThat(ownerInDb).contains(savedOwner);
    }

    @Test
    public void saveUpsertTest() {

        var owner = Owner.builder()
                .id(123)
                .name("Jane Marie")
                .preferredName("Jany")
                .build();
        var anotherOwner = Owner.builder()
                .id(123)
                .name("John")
                .preferredName("John")
                .build();

        ownerRepository.save(owner);
        ownerRepository.save(anotherOwner);

        var ownerInDb = ownerRepository.findAll();

        assertThat(ownerInDb).hasSize(1);
        assertThat(ownerInDb).contains(anotherOwner);
    }


    @Test
    public void uniqueConstraint() {

        var owner = Owner.builder()
                .id(123)
                .email("mail@mail.com")
                .name("Jane Marie")
                .build();
        var anotherOwner = Owner.builder()
                .id(456)
                .email("mail@mail.com")
                .name("John Smith")
                .build();

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

    @Test
    public void findByMultipleCriteria() {
        var owner = Owner.builder()
                .id(123)
                .email("mail@email.com")
                .name("Jane Marie")
                .preferredName("Jany")
                .build();
        var anotherOwner = Owner.builder()
                .id(456)
                .email("anotherEmail@mail.com")
                .name("Jane Marie")
                .preferredName("Mary")
                .build();

        ownerRepository.saveAll(List.of(owner, anotherOwner));

        var found = ownerRepository.findOwnerByNameAndPreferredName("Jane Marie", "Jany");

        assertThat(found).isEqualTo(owner);

    }

    @Test
    public void findByMultipleCriteriaWithNull() {
        var owner = Owner.builder()
                .id(123)
                .email("mail@email.com")
                .name("Jane Marie")
                .preferredName("Jany").
                build();
        var anotherOwner = Owner.builder()
                .id(456)
                .email("anotherEmail@mail.com")
                .name("Jane Marie")
                .build();

        ownerRepository.saveAll(List.of(owner, anotherOwner));

        var found = ownerRepository.findOwnerByNameAndPreferredName("Jane Marie", null);

        assertThat(found).isEqualTo(anotherOwner);

    }

    @Test
    public void findByMultipleCriteriaOr() {
        var owner = Owner.builder()
                .id(123)
                .email("mail@email.com")
                .name("Jane Marie")
                .preferredName("Jany")
                .build();
        var anotherOwner = Owner.builder()
                .id(456)
                .email("anotherEmail@mail.com")
                .name("Jane Marie")
                .preferredName("Mary")
                .build();
        var yetAnotherOwner = Owner.builder()
                .id(789)
                .email("yetAnotherEmail@mail.com")
                .name("Janice Jane")
                .preferredName("Jany")
                .build();

        ownerRepository.saveAll(List.of(owner, anotherOwner, yetAnotherOwner));

        var found = ownerRepository.findOwnersByNameOrPreferredName("Jane Marie", "Jany");

        assertThat(found).hasSize(3);
        assertThat(found).contains(owner, anotherOwner, yetAnotherOwner);

    }

    @Test
    @SneakyThrows
    public void findByDate() {
        var owner = Owner.builder()
                .id(123)
                .email("mail@email.com")
                .name("Jane Marie")
                .joinedAt(parseDate("2018-05-05 11:50:55"))
                .build();
        var anotherOwner = Owner.builder()
                .id(456)
                .email("anotherEmail@mail.com")
                .name("John Smith")
                .joinedAt(parseDate("2020-07-09 14:00:00"))
                .build();

        var yetAnotherOwner = Owner.builder()
                .id(789)
                .email("yetAnotherEmail@mail.com")
                .name("Jack Jackson")
                .joinedAt(parseDate("1985-01-01 10:18:00"))
                .build();

        ownerRepository.saveAll(List.of(owner, anotherOwner, yetAnotherOwner));

        var found = ownerRepository.findOwnerByJoinedAt(parseDate("2020-07-09 14:00:00"));

        assertThat(found.getId()).isEqualTo(anotherOwner.getId());

    }

    @Test
    @SneakyThrows
    public void findByDateAfter() {
        var owner = Owner.builder()
                .id(123)
                .email("mail@email.com")
                .name("Jane Marie")
                .joinedAt(parseDate("2018-05-05 11:50:55"))
                .build();
        var anotherOwner = Owner.builder()
                .id(456)
                .email("anotherEmail@mail.com")
                .name("John Smith")
                .joinedAt(parseDate("2020-07-09 14:00:00"))
                .build();

        var yetAnotherOwner = Owner.builder()
                .id(789)
                .email("yetAnotherEmail@mail.com")
                .name("Jack Jackson")
                .joinedAt(parseDate("1985-01-01 10:18:00"))
                .build();

        ownerRepository.saveAll(List.of(owner, anotherOwner, yetAnotherOwner));

        var found = ownerRepository.findOwnersByJoinedAtAfter(parseDate("2015-01-01 00:00:00"));

        assertThat(found).hasSize(2);
        assertThat(found).extracting("id").contains(123, 456);

    }

    @Test
    @SneakyThrows
    public void findByDateBefore() {
        var owner = Owner.builder()
                .id(123)
                .email("mail@email.com")
                .name("Jane Marie")
                .joinedAt(parseDate("2018-05-05 11:50:55"))
                .build();
        var anotherOwner = Owner.builder()
                .id(456)
                .email("anotherEmail@mail.com")
                .name("John Smith")
                .joinedAt(parseDate("2020-07-09 14:00:00"))
                .build();

        var yetAnotherOwner = Owner.builder()
                .id(789)
                .email("yetAnotherEmail@mail.com")
                .name("Jack Jackson")
                .joinedAt(parseDate("1985-01-01 10:18:00"))
                .build();

        ownerRepository.saveAll(List.of(owner, anotherOwner, yetAnotherOwner));

        var found = ownerRepository.findOwnersByJoinedAtBefore(parseDate("2015-01-01 00:00:00"));

        assertThat(found).hasSize(1);
        assertThat(found).extracting("id").contains(789);

    }

    @Test
    @SneakyThrows
    public void findByDateBetween() {
        var owner = Owner.builder()
                .id(123)
                .email("mail@email.com")
                .name("Jane Marie")
                .joinedAt(parseDate("2018-05-05 11:50:55"))
                .build();
        var anotherOwner = Owner.builder()
                .id(456)
                .email("anotherEmail@mail.com")
                .name("John Smith")
                .joinedAt(parseDate("2020-07-09 14:00:00"))
                .build();

        var yetAnotherOwner = Owner.builder()
                .id(789)
                .email("yetAnotherEmail@mail.com")
                .name("Jack Jackson")
                .joinedAt(parseDate("1985-01-01 10:18:00"))
                .build();

        ownerRepository.saveAll(List.of(owner, anotherOwner, yetAnotherOwner));

        var found = ownerRepository.findFirstByJoinedAtBetween(parseDate("2015-01-01 00:00:00"), parseDate("2020-01-01 00:00:00"));

        assertThat(found.getId()).isEqualTo(123);

    }


    private Date parseDate(String input) throws ParseException {
        return simpleDateFormat.parse(input);
    }


}
