package com.jobassistance.repository;

import com.jobassistance.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ドキュメントリポジトリ
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByWorkerId(Long workerId);

    List<Document> findByWorkerIdAndDocumentType(Long workerId, String documentType);
}
