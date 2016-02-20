package ru.javaops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.model.Payment;

@Transactional(readOnly = true)
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}