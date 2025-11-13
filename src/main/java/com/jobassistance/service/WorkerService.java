package com.jobassistance.service;

import com.jobassistance.entity.Worker;
import com.jobassistance.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 就労者サービスクラス
 */
@Service
@Transactional
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 就労者一覧取得
     */
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    /**
     * 就労者登録
     */
    public Worker createWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    /**
     * 就労者詳細取得
     */
    public Optional<Worker> getWorkerById(Long id) {
        return workerRepository.findById(id);
    }

    /**
     * 就労者更新
     */
    public Worker updateWorker(Long id, Worker worker) {
        worker.setId(id);
        return workerRepository.save(worker);
    }

    /**
     * 就労者削除
     */
    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }

    /**
     * 就労者存在確認
     */
    public boolean existsById(Long id) {
        return workerRepository.existsById(id);
    }

    /**
     * 名前で検索
     */
    public List<Worker> searchByName(String name) {
        return workerRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * 国籍で検索
     */
    public List<Worker> searchByNationality(String nationality) {
        return workerRepository.findByNationality(nationality);
    }

    /**
     * ステータスで検索
     */
    public List<Worker> searchByStatus(String status) {
        return workerRepository.findByCurrentStatus(status);
    }
}
