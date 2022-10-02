package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Farm;
import com.codely.jpajava.domain.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
class FarmRepositoryTest {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    public void setUp() {
        farmRepository.deleteAll();
        locationRepository.deleteAll();
    }

    @Test
    public void saveOneElementTest() {
        var farm = Farm.builder()
                .name("a farm")
                .location(Location.builder()
                        .latitude(12.3)
                        .longitude(34.0)
                        .build()).
                build();
        farmRepository.save(farm);

        var farmInDb = farmRepository.findAll();

        assertThat(farmInDb).hasSize(1);
        assertThat(farmInDb).extracting("name").containsExactly("a farm");
        assertThat(farmInDb).extracting("location").extracting("latitude", "longitude").contains(tuple(12.3, 34.0));

        var locationInDb = locationRepository.findAll();
        assertThat(locationInDb).hasSize(1);
        assertThat(locationInDb).extracting("latitude", "longitude").contains(tuple(12.3, 34.0));
        assertThat(locationInDb).extracting("farm").extracting("name").contains("a farm");

    }

    @Test
    public void insertExistingLocationTest() {
        var location = Location.builder()
                .latitude(12.3)
                .longitude(34.0)
                .build();

        location = locationRepository.save(location);

        var aFarm = Farm.builder()
                .name("a farm")
                .location(location)
                .build();

        assertThatExceptionOfType(InvalidDataAccessApiUsageException.class)
                .isThrownBy(() -> farmRepository.save(aFarm));
    }

    @Test
    public void violateOneToOneTest() {
        var location = Location.builder()
                .latitude(12.3)
                .longitude(34.0)
                .build();

        var aFarm = Farm.builder()
                .name("a farm")
                .location(location)
                .build();

        farmRepository.save(aFarm);

        var anotherFarm = Farm.builder()
                .name("another farm")
                .location(location)
                .build();

        assertThatExceptionOfType(InvalidDataAccessApiUsageException.class)
                .isThrownBy(() -> farmRepository.save(anotherFarm));

    }

    @Test
    public void deleteOneElementTest() {
        var farm = Farm.builder()
                .name("a farm")
                .location(Location.builder()
                        .latitude(12.3)
                        .longitude(34.0)
                        .build()).
                build();
        farmRepository.save(farm);

        assertThat(farmRepository.findAll()).hasSize(1);
        assertThat(locationRepository.findAll()).hasSize(1);

        farmRepository.deleteById(farm.getId());

        assertThat(farmRepository.findAll()).hasSize(0);
        assertThat(locationRepository.findAll()).hasSize(0);
    }

    @Test
    public void findByLocationTest() {
        var location = Location.builder()
                .latitude(12.3)
                .longitude(34.0)
                .build();

        var aFarm = Farm.builder()
                .name("a farm")
                .location(location)
                .build();

        var anotherFarm = Farm.builder()
                .name("another farm")
                .location(Location.builder()
                        .latitude(15.7)
                        .longitude(34.9)
                        .build())
                .build();

        farmRepository.saveAll(List.of(aFarm, anotherFarm));

        var farmInDb = farmRepository.findFarmByLocation(location);

        assertThat(farmInDb).extracting("name").isEqualTo("a farm");

    }

    @Test
    public void countByLocationLatitudeTest() {

        var aFarm = Farm.builder()
                .name("a farm")
                .location(Location.builder()
                        .latitude(12.3)
                        .longitude(34.0)
                        .build())
                .build();

        var anotherFarm = Farm.builder()
                .name("another farm")
                .location(Location.builder()
                        .latitude(12.3)
                        .longitude(34.9)
                        .build())
                .build();

        farmRepository.saveAll(List.of(aFarm, anotherFarm));

        assertThat(farmRepository.countFarmsByLocationLatitude(12.3)).isEqualTo(2);

    }


}
