package com.example.rest.data.spring.repository;

import com.example.rest.data.spring.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByName(@Param("name") String name);

    List<User> findByCountry(@Param("country") String country);
}
