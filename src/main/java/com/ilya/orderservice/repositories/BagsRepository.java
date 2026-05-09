package com.ilya.orderservice.repositories;

import com.ilya.orderservice.models.Bag;
import com.ilya.orderservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BagsRepository extends JpaRepository<Bag, Long> {

    List<Bag> findAllByUser(User user);

    void deleteAllByUser(User user);
}
