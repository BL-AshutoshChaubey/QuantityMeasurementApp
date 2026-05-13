package com.bridgelabz.quantitymeasurementApp.repository;

import com.bridgelabz.quantitymeasurementApp.entity.ArithmeticHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArithmeticHistoryRepository extends JpaRepository<ArithmeticHistory, Long> {
    List<ArithmeticHistory> findByUserEmailOrderByCreatedAtDesc(String email);
}
