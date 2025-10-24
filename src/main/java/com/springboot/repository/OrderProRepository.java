package com.springboot.repository;

import com.springboot.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProRepository extends JpaRepository<Order, Long> {

}
