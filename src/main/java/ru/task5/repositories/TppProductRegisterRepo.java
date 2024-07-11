package ru.task5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.task5.entity.TppProductRegister;

public interface TppProductRegisterRepo extends JpaRepository<TppProductRegister, Long> {
}
