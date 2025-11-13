package com.jobassistance.repository;

import com.jobassistance.entity.CareerPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * キャリアパスリポジトリ
 */
@Repository
public interface CareerPathRepository extends JpaRepository<CareerPath, Long> {

    /**
     * 就労者IDでキャリアパスを検索する
     * 
     * @param workerId 就労者ID
     * @return 該当するキャリアパスのリスト
     */
    List<CareerPath> findByWorkerId(Long workerId);
}
