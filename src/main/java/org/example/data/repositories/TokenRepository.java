package org.example.data.repositories;

import org.example.data.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findByTokenAndEmail(String token, String email);

    void deleteByTokenAndEmail(String token, String email); // Custom delete method

    Optional<Token> findByToken(String token);

    Optional<Token> findByEmail(String email);
}