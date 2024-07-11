package ru.task5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.task5.entity.Agreement;

@Component
public interface AgreementRepo extends JpaRepository<Agreement, Long> {
}
