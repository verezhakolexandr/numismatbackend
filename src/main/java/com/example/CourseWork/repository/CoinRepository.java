package com.example.CourseWork.repository;

import com.example.CourseWork.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Long> {
}
