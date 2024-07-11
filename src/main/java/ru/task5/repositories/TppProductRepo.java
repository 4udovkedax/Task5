package ru.task5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.task5.entity.TppProduct;

public interface TppProductRepo extends JpaRepository<TppProduct, Long> {
}
