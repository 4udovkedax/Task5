package ru.task5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.task5.entity.AccountPool;

public interface AccountPoolRepo extends JpaRepository<AccountPool, Long> {
}
