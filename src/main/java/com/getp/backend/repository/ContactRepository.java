package com.getp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.getp.backend.entity.ContactMessage;

public interface ContactRepository extends JpaRepository<ContactMessage, Long> {

}