package com.ilya.orderservice.repositories;

import com.ilya.orderservice.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodsRepository extends JpaRepository<Food, Long> {
    public Optional<Food> findByName(String name);
}
