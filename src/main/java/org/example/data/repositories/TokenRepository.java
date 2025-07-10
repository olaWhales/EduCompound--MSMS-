package org.example.data.repositories;

import org.example.data.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenAndEmail(String token, String email);

    void deleteByTokenAndEmail(String token, String email); // Custom delete method
}