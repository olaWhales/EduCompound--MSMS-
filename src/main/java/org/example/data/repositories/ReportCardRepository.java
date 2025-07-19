package org.example.data.repositories;


import org.example.data.model.ReportCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportCardRepository extends JpaRepository<ReportCard, UUID> {}


