package com.codely.jpajava.application.repository;

import com.codely.jpajava.domain.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {

    Owner findOwnerByNameAndPreferredName(String name, String preferredName);

    List<Owner> findOwnersByNameOrPreferredName(String name, String preferredName);

    Owner findOwnerByJoinedAt(Date date);

    List<Owner> findOwnersByJoinedAtAfter(Date date);

    List<Owner> findOwnersByJoinedAtBefore(Date date);

    Owner findFirstByJoinedAtBetween(Date before, Date after);
}
