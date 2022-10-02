package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TreeRepository extends JpaRepository<Tree, String> {

    List<Tree> findAllByForestsId(Integer id);

    List<Tree> findDistinctByForestsIdIn(Set<Integer> ids);
}
