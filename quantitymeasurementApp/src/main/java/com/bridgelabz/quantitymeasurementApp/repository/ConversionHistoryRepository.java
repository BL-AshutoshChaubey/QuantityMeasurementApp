package com.bridgelabz.quantitymeasurementApp.repository;

import com.bridgelabz.quantitymeasurementApp.entity.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionHistoryRepository extends JpaRepository<ConversionHistory,Long>{
}
