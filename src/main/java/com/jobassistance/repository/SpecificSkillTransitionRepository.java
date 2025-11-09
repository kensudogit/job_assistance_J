package com.jobassistance.repository;

import com.jobassistance.entity.SpecificSkillTransition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 特定技能移行リポジトリ
 */
@Repository
public interface SpecificSkillTransitionRepository extends JpaRepository<SpecificSkillTransition, Long> {
    
    List<SpecificSkillTransition> findByWorkerId(Long workerId);
}

