package ru.javaops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.model.MailCase;

@Transactional(readOnly = true)
public interface MailCaseRepository extends JpaRepository<MailCase, Integer> {
}