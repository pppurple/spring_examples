package com.example.rest.data.spring.repository;

import com.example.rest.data.spring.entity.Child;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "children")
public interface ChildRepository extends PagingAndSortingRepository<Child, Long> {
}
