package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Farm;
import com.codely.jpajava.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Integer> {

    Farm findFarmByLocation(Location location);

    Integer countFarmsByLocationLatitude(Double latitude);

}
