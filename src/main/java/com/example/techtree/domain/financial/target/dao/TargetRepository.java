package com.example.techtree.domain.financial.target.dao;

import com.example.techtree.domain.financial.target.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TargetRepository extends JpaRepository<Target, Long> {
    @Query("SELECT t.targetName FROM Target t")
    List<String> findAllTargetNames();
}
