package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {
}
