package org.example.data.repositories;

import org.example.data.model.SchoolBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<SchoolBranch, Long> {
}
