package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Foreman;
import com.codely.jpajava.domain.ManagedFarm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ManagedFarmRepositoryTest {

    @Autowired
    private ManagedFarmRepository managedFarmRepository;

    @Autowired
    private ForemanRepository foremanRepository;

    @BeforeEach
    void setUp() {
        managedFarmRepository.deleteAll();
        foremanRepository.deleteAll();
    }

    @Test
    public void saveOneElementTest() {
        var foreman = Foreman.builder().id(1).name("James").build();
        var anotherForeman = Foreman.builder().id(2).name("Kaily").build();

        var managedFarm = ManagedFarm.builder()
                .id(1)
                .name("small farm")
                .foremen(List.of(foreman, anotherForeman))
                .build();

        managedFarmRepository.save(managedFarm);

        assertThat(managedFarmRepository.findAll()).hasSize(1);
        assertThat(foremanRepository.findAll()).hasSize(2);
    }


    @Test
    public void saveOneElementPreviouslyExistingTest() {
        var foreman = Foreman.builder().id(1).name("James").build();
        var anotherForeman = Foreman.builder().id(2).name("Kaily").build();
        foremanRepository.saveAll(List.of(foreman, anotherForeman));

        var managedFarm = ManagedFarm.builder()
                .id(1)
                .name("small farm")
                .foremen(List.of(foreman, anotherForeman))
                .build();

        managedFarmRepository.save(managedFarm);

        assertThat(managedFarmRepository.findAll()).hasSize(1);
        assertThat(foremanRepository.findAll()).hasSize(2);

    }

    @Test
    public void deleteAlsoDeleteForemanTest() {
        var foreman = Foreman.builder().id(1).name("James").build();
        var anotherForeman = Foreman.builder().id(2).name("Kaily").build();

        var managedFarm = ManagedFarm.builder()
                .id(1)
                .name("small farm")
                .foremen(List.of(foreman, anotherForeman))
                .build();

        managedFarmRepository.save(managedFarm);

        var managedFarmInDb = managedFarmRepository.findFirstByName("small farm");

        assertThat(managedFarmInDb.getForemen()).hasSize(2);

        managedFarmRepository.deleteById(1);

        assertThat(managedFarmRepository.findAll()).hasSize(0);
        assertThat(foremanRepository.findAll()).hasSize(0);

    }

    @Test
    public void violateConstraint() {
        var foreman = Foreman.builder().id(1).name("James").build();
        var anotherForeman = Foreman.builder().id(2).name("Kaily").build();

        var managedFarm = ManagedFarm.builder()
                .id(1)
                .name("small farm")
                .foremen(List.of(foreman, anotherForeman))
                .build();

        managedFarmRepository.save(managedFarm);

        assertThat(managedFarmRepository.findFirstByName("small farm").getForemen()).hasSize(2);

        var anotherManagedFarm = ManagedFarm.builder()
                .id(2)
                .name("another farm")
                .foremen(List.of(foreman, anotherForeman))
                .build();

        managedFarmRepository.save(anotherManagedFarm);

        assertThat(managedFarmRepository.findFirstByName("small farm").getForemen()).hasSize(0);
        assertThat(managedFarmRepository.findFirstByName("another farm").getForemen()).hasSize(2);

    }

    @Test
    public void checkForemanDataTest() {
        var foreman = Foreman.builder().id(1).name("James").build();
        var anotherForeman = Foreman.builder().id(2).name("Kaily").build();

        var managedFarm = ManagedFarm.builder()
                .id(1)
                .name("small farm")
                .foremen(List.of(foreman, anotherForeman))
                .build();

        managedFarmRepository.save(managedFarm);

        var managedFarmInDb = managedFarmRepository.findFirstByName("small farm");

        assertThat(managedFarmInDb.getForemen()).extracting("name").contains("James", "Kaily");
    }

    @Test
    public void findFarmByForemanTest() {
        var foreman = Foreman.builder().id(1).name("James").build();
        var anotherForeman = Foreman.builder().id(2).name("Kaily").build();

        var managedFarm = ManagedFarm.builder()
                .id(1)
                .name("small farm")
                .foremen(List.of(foreman, anotherForeman))
                .build();

        managedFarmRepository.save(managedFarm);

        var foremanInFarm = managedFarmRepository.findByForemenName("James");

        assertThat(foremanInFarm.getName()).isEqualTo("small farm");
    }


}
