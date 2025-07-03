package org.example.data.repositories;


import org.example.data.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeRepository extends JpaRepository<Fee, String> {}
