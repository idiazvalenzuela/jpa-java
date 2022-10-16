package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Vegetable;
import com.codely.jpajava.domain.VegetableId;
import com.codely.jpajava.domain.valueobject.VegetableType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VegetableRepository extends JpaRepository<Vegetable, VegetableId> {

    List<Vegetable> findVegetablesByTypeOrderByAverageWeight(VegetableType type, Sort sort);

    List<Vegetable> findAllByNameLike(String like);
}
