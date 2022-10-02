package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Forest;
import com.codely.jpajava.domain.Tree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ForestRepositoryTest {

    @Autowired
    private ForestRepository forestRepository;

    @Autowired
    private TreeRepository treeRepository;

    @BeforeEach
    void setUp() {
        forestRepository.deleteAll();
        treeRepository.deleteAll();
    }

    @Test
    public void saveOneElementTest() {
        var tree = Tree.builder().name("oak").build();
        treeRepository.save(tree);

        var forest = Forest.builder()
                .id(1)
                .name("small forest")
                .trees(Set.of(tree))
                .build();

        forestRepository.save(forest);

        var forestsInDb = forestRepository.findAll();
        var treesInDb = treeRepository.findAll();

        assertThat(forestsInDb).isNotEmpty();
        assertThat(forestsInDb)
                .flatExtracting("trees")
                .extracting("name")
                .contains("oak");

        assertThat(treesInDb).isNotEmpty();
        assertThat(treesInDb).flatExtracting("forests")
                .extracting("name")
                .contains("small forest");

    }


    @Test
    public void saveManyElementTest() {
        var oak = Tree.builder().name("oak").build();
        var palm = Tree.builder().name("palm").build();
        var olive = Tree.builder().name("olive").build();
        treeRepository.saveAll(List.of(oak, palm, olive));

        var smallForest = Forest.builder()
                .id(1)
                .name("small forest")
                .trees(Set.of(oak))
                .build();

        var mediumForest = Forest.builder()
                .id(2)
                .name("medium forest")
                .trees(Set.of(oak, olive))
                .build();

        var bigForest = Forest.builder()
                .id(3)
                .name("big forest")
                .trees(Set.of(oak, olive, palm))
                .build();

        forestRepository.saveAll(List.of(smallForest, mediumForest, bigForest));

        assertThat(forestRepository.findAll()).hasSize(3);
        assertThat(treeRepository.findAll()).hasSize(3);

        assertThat(forestRepository.findById(1).get().getTrees()).extracting("name").contains("oak");
        assertThat(forestRepository.findById(2).get().getTrees()).extracting("name").containsExactlyInAnyOrder("oak", "olive");
        assertThat(forestRepository.findById(3).get().getTrees()).extracting("name").containsExactlyInAnyOrder("oak", "olive", "palm");

        assertThat(treeRepository.findById("oak").get().getForests()).extracting("id").containsExactlyInAnyOrder(1, 2, 3);
        assertThat(treeRepository.findById("palm").get().getForests()).extracting("id").containsExactlyInAnyOrder(3);
        assertThat(treeRepository.findById("olive").get().getForests()).extracting("id").containsExactlyInAnyOrder(2, 3);

    }

    @Test
    public void findAllByTreesTest() {
        var oak = Tree.builder().name("oak").build();
        var palm = Tree.builder().name("palm").build();
        var olive = Tree.builder().name("olive").build();
        treeRepository.saveAll(List.of(oak, palm, olive));

        var smallForest = Forest.builder()
                .id(1)
                .name("small forest")
                .trees(Set.of(oak))
                .build();

        var mediumForest = Forest.builder()
                .id(2)
                .name("medium forest")
                .trees(Set.of(oak, olive))
                .build();

        var bigForest = Forest.builder()
                .id(3)
                .name("big forest")
                .trees(Set.of(oak, olive, palm))
                .build();

        forestRepository.saveAll(List.of(smallForest, mediumForest, bigForest));

        var forestsWithOlives = forestRepository.findAllByTrees(palm);

        assertThat(forestsWithOlives).hasSize(1);
        assertThat(forestsWithOlives).extracting("id").containsExactlyInAnyOrder(3);

    }

    @Test
    public void findAllByTreesNameTest() {
        var oak = Tree.builder().name("oak").build();
        var palm = Tree.builder().name("palm").build();
        var olive = Tree.builder().name("olive").build();
        treeRepository.saveAll(List.of(oak, palm, olive));

        var smallForest = Forest.builder()
                .id(1)
                .name("small forest")
                .trees(Set.of(oak))
                .build();

        var mediumForest = Forest.builder()
                .id(2)
                .name("medium forest")
                .trees(Set.of(oak, olive))
                .build();

        var bigForest = Forest.builder()
                .id(3)
                .name("big forest")
                .trees(Set.of(oak, olive, palm))
                .build();

        forestRepository.saveAll(List.of(smallForest, mediumForest, bigForest));

        var forestsWithOlives = forestRepository.findAllByTreesName("olive");

        assertThat(forestsWithOlives).hasSize(2);
        assertThat(forestsWithOlives).extracting("id").containsExactlyInAnyOrder(2, 3);

    }


    @Test
    public void findAllByTreesInTest() {
        var oak = Tree.builder().name("oak").build();
        var palm = Tree.builder().name("palm").build();
        var olive = Tree.builder().name("olive").build();
        treeRepository.saveAll(List.of(oak, palm, olive));

        var smallForest = Forest.builder()
                .id(1)
                .name("small forest")
                .trees(Set.of(oak))
                .build();

        var mediumForest = Forest.builder()
                .id(2)
                .name("medium forest")
                .trees(Set.of(olive))
                .build();

        var bigForest = Forest.builder()
                .id(3)
                .name("big forest")
                .trees(Set.of(oak, palm))
                .build();

        forestRepository.saveAll(List.of(smallForest, mediumForest, bigForest));

        var forestsWithOlivesOrOaks = forestRepository.findAllByTreesIn(Set.of(oak, olive));

        assertThat(forestsWithOlivesOrOaks).hasSize(3);
        assertThat(forestsWithOlivesOrOaks).extracting("id").containsExactlyInAnyOrder(1, 2, 3);

    }

    @Test
    public void findAllByForestIdTest() {
        var oak = Tree.builder().name("oak").build();
        var palm = Tree.builder().name("palm").build();
        var olive = Tree.builder().name("olive").build();
        treeRepository.saveAll(List.of(oak, palm, olive));

        var smallForest = Forest.builder()
                .id(1)
                .name("small forest")
                .trees(Set.of(oak))
                .build();

        var mediumForest = Forest.builder()
                .id(2)
                .name("medium forest")
                .trees(Set.of(oak, olive))
                .build();

        var bigForest = Forest.builder()
                .id(3)
                .name("big forest")
                .trees(Set.of(oak, olive, palm))
                .build();

        forestRepository.saveAll(List.of(smallForest, mediumForest, bigForest));

        var treesInForest2 = treeRepository.findAllByForestsId(2);

        assertThat(treesInForest2).hasSize(2);
        assertThat(treesInForest2).extracting("name").containsExactlyInAnyOrder("oak", "olive");

    }

    @Test
    public void findAllByForestIdInTest() {
        var oak = Tree.builder().name("oak").build();
        var palm = Tree.builder().name("palm").build();
        var olive = Tree.builder().name("olive").build();
        treeRepository.saveAll(List.of(oak, palm, olive));

        var smallForest = Forest.builder()
                .id(1)
                .name("small forest")
                .trees(Set.of(oak))
                .build();

        var mediumForest = Forest.builder()
                .id(2)
                .name("medium forest")
                .trees(Set.of(oak, olive))
                .build();

        var bigForest = Forest.builder()
                .id(3)
                .name("big forest")
                .trees(Set.of(oak, olive, palm))
                .build();

        forestRepository.saveAll(List.of(smallForest, mediumForest, bigForest));

        var treesInForests = treeRepository.findDistinctByForestsIdIn(Set.of(1, 3));

        assertThat(treesInForests).hasSize(3);
        assertThat(treesInForests).extracting("name").containsExactlyInAnyOrder("oak", "olive", "palm");

    }


}
