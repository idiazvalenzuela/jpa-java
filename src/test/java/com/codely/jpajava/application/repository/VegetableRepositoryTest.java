package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Vegetable;
import com.codely.jpajava.domain.valueobject.VegetableType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VegetableRepositoryTest {

    @Autowired
    private VegetableRepository vegetableRepository;

    @BeforeEach
    public void setUp() {
        vegetableRepository.deleteAll();
    }

    @Test
    public void saveOneElementTest() {
        var vegetable = Vegetable.builder()
                .name("Carrot")
                .family("Apiaceae")
                .type(VegetableType.TUBER)
                .averageWeight(35.6)
                .build();

        vegetableRepository.save(vegetable);

        var vegetableInDb = vegetableRepository.findAll();

        assertThat(vegetableInDb).containsExactly(vegetable);

    }

    @Test
    public void orderByWeightTest() {
        var carrot = Vegetable.builder()
                .name("Carrot")
                .family("Apiaceae")
                .type(VegetableType.TUBER)
                .averageWeight(35.6)
                .build();
        var potato = Vegetable.builder()
                .name("Potato")
                .family("Solanaceace")
                .type(VegetableType.TUBER)
                .averageWeight(100.0)
                .build();
        var lettuce = Vegetable.builder()
                .name("Lettuce")
                .family("Compositae")
                .type(VegetableType.WITH_LEAVES)
                .averageWeight(35.6)
                .build();

        vegetableRepository.saveAll(List.of(carrot, potato, lettuce));

        var tubers = vegetableRepository.findVegetablesByTypeOrderByAverageWeight(VegetableType.TUBER, Sort.unsorted().descending());

        assertThat(tubers).hasSize(2);
        assertThat(tubers).containsExactly(carrot, potato);
    }

    @Test
    public void findByNameLikeTest() {
        var carrot = Vegetable.builder()
                .name("Carrot")
                .family("Apiaceae")
                .type(VegetableType.TUBER)
                .averageWeight(35.6)
                .build();
        var potato = Vegetable.builder()
                .name("Potato")
                .family("Solanaceace")
                .type(VegetableType.TUBER)
                .averageWeight(100.0)
                .build();
        var lettuce = Vegetable.builder()
                .name("Lettuce")
                .family("Compositae")
                .type(VegetableType.WITH_LEAVES)
                .averageWeight(35.6)
                .build();

        vegetableRepository.saveAll(List.of(carrot, potato, lettuce));

        assertThat(vegetableRepository.findAllByNameLike("Lett%")).hasSize(1);

    }
}
