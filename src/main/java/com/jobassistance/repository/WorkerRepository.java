package com.jobassistance.repository;

import com.jobassistance.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 就労者リポジトリ
 */
@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    /**
     * メールアドレスで就労者を検索する
     * 
     * @param email メールアドレス
     * @return 該当する就労者（存在しない場合は空）
     */
    Optional<Worker> findByEmail(String email);

    /**
     * 名前（部分一致、大文字小文字を区別しない）で就労者を検索する
     * 
     * @param name 検索する名前
     * @return 該当する就労者のリスト
     */
    List<Worker> findByNameContainingIgnoreCase(String name);

    /**
     * 国籍で就労者を検索する
     * 
     * @param nationality 国籍
     * @return 該当する就労者のリスト
     */
    List<Worker> findByNationality(String nationality);

    /**
     * 現在のステータスで就労者を検索する
     * 
     * @param status ステータス
     * @return 該当する就労者のリスト
     */
    List<Worker> findByCurrentStatus(String status);
}
