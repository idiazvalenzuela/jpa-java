package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Forest;
import com.codely.jpajava.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ForestRepository extends JpaRepository<Forest, Integer> {

    List<Forest> findAllByTreesIn(Set<Tree> trees);

    List<Forest> findAllByTrees(Tree tree);

    List<Forest> findAllByTreesName(String name);
}
