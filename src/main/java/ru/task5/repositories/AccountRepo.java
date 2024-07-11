package ru.task5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.task5.entity.Account;

public interface AccountRepo extends JpaRepository<Account, Long> {
}
