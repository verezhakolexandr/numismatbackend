package com.example.CourseWork.repository;

import com.example.CourseWork.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
    History findByHistoryBody(String historyBody);
}
