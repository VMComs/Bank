package com.bank.history.repository;

import com.bank.history.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * интерфейс для работы с БД, наследуется от JpaRepository, чтобы были используемые методы
 */

public interface HistoryRepository extends JpaRepository<History, Long> {


}
