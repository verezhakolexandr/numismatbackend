package com.example.CourseWork.repository;

import com.example.CourseWork.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByName(String name);
}
