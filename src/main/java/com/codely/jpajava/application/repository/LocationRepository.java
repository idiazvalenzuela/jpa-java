package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Integer> {
}
