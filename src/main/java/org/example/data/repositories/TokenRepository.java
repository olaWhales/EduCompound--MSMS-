package org.example.data.repositories;

import org.example.data.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenAndEmail(String token, String email);
    void deleteByTokenAndEmail(String token, String email);
}