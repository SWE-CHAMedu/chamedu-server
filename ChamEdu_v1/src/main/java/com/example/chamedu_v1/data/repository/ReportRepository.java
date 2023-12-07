package com.example.chamedu_v1.data.repository;


import com.example.chamedu_v1.data.entity.Report;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {
    @Transactional
    Report save(Report report);

}
