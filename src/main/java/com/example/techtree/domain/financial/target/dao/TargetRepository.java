package com.example.techtree.domain.financial.target.dao;

import com.example.techtree.domain.financial.target.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetRepository extends JpaRepository<Target, Long> {
}
