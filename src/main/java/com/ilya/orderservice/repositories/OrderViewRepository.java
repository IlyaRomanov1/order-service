package com.ilya.orderservice.repositories;

import com.ilya.orderservice.dtos.OrderViewDto;
import com.ilya.orderservice.models.OrderView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderViewRepository extends JpaRepository<OrderView, Long> {
    OrderView findByUserId(Long userId);
}
