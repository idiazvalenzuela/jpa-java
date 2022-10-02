package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.ManagedFarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagedFarmRepository extends JpaRepository<ManagedFarm, Integer> {

    ManagedFarm findFirstByName(String name);

    ManagedFarm findByForemenName(String name);

}
