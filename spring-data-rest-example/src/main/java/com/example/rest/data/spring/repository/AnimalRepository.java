package com.example.rest.data.spring.repository;

import com.example.rest.data.spring.entity.Animal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

// @RepositoryRestResource(exported = false)
public interface AnimalRepository extends PagingAndSortingRepository<Animal, Long> {
    // @RestResource(exported = false)
    List<Animal> findByName(@Param("name") String name);

    /*
    @Override
    @RestResource(exported = false)
    void delete(Long id);
    */
}
