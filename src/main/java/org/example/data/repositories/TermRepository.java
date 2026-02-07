package org.example.data.repositories;

import jakarta.transaction.Transactional;
import org.example.data.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TermRepository extends JpaRepository<Term, UUID> {
//    boolean existsByAdminTenantAndStartDateLessThanEqualAndEndDateGreaterThanEqual(AdminTenant adminTenant, Date endDate, Date startDate);

//    boolean existsByAdminTenantAndStartDateAndEndDate(AdminTenant adminTenant, Date startDate, Date endDate);
    boolean existsByAdminTenant_TenantIdAndStartDateAndEndDate(
            UUID tenantId,
            Date startDate,
            Date endDate
    );

//    Optional<Term> findByAdminTenantAndIsActiveTrue(AdminTenant tenant);
//    Optional<Term> findByAdminTenantAndStatus(
//            AdminTenant tenant,
//            TermStatus status
//    );

//    // ✅ Custom JPQL query to deactivate all active sessions for a tenant
//    @Transactional
//    @Modifying
//    @Query("UPDATE Session s SET s.isActive = false WHERE s.adminTenant.tenantId = :tenantId AND s.isActive = true")
//    void deactivateAllForTenant(@Param("tenantId") UUID tenantId);



        @Modifying
        @Transactional
        @Query("""
    UPDATE Term t
    SET t.status = org.example.data.model.TermStatus.COMPLETED
    WHERE t.adminTenant.tenantId = :tenantId
      AND t.status = org.example.data.model.TermStatus.ACTIVE
    """)
        void deactivateAllForTenant(@Param("tenantId") UUID tenantId);


    List<Term> findByAdminTenant_TenantId(UUID tenantId);

//@Query("""
//    SELECT s FROM Session s
//    WHERE s.adminTenant.tenantId = :tenantId
//      AND s.status = org.example.data.model.TermStatus.ACTIVE
//""")
//Optional<Session> findActiveSessionForTenant(
//        @Param("tenantId") UUID tenantId
//);

//    Optional<Session> findByAdminTenant_TenantIdAndStatus(
//            UUID tenantId,
//            SessionStatus status
//    );

    List<Term> findAllByAdminTenant_TenantIdAndStatus(UUID tenantId, TermStatus termStatus);

//    @Query("SELECT s FROM Session s " +
//            "WHERE s.adminTenant.tenantId = :tenantId " +
//            "AND (:branchId IS NULL OR s.schoolBranch.id = :branchId) " +  // <-- use `id`
//            "AND s.startDate <= :end AND s.endDate >= :start")
//    List<Session> findOverlappingSessions(
//            UUID tenantId,
//            UUID branchId,
//            Date start,
//            Date end
//    );

//    @Query("SELECT s FROM Session s " +
//            "WHERE s.adminTenant.tenantId = :tenantId " +
//            "AND s.status NOT IN (org.example.data.model.TermStatus.CLOSED, org.example.data.model.TermStatus.ARCHIVED) " +
//            "AND s.startDate <= :end AND s.endDate >= :start")
//    List<Session> findActiveOrPlannedOverlappingSessions(
//            @Param("tenantId") UUID tenantId,
//            @Param("start") Date start,
//            @Param("end") Date end
//    );
//
//    @Query("""
//    SELECT s FROM Session s
//    WHERE s.adminTenant.tenantId = :tenantId
//      AND (:branchId IS NULL OR s.schoolBranch.id = :branchId)
//      AND s.status IN (org.example.data.model.SessionStatus.PLANNED, org.example.data.model.SessionStatus.ACTIVE)
//      AND s.startDate <= :end
//      AND s.endDate >= :start
//""")
//    List<Term> findActiveOrPlannedOverlappingSessions(
//            @Param("tenantId") UUID tenantId,
//            @Param("branchId") UUID branchId,
//            @Param("start") Date start,
//            @Param("end") Date end
//    );

    @Query("""
SELECT t FROM Term t
WHERE t.adminTenant.tenantId = :tenantId
  AND (:branchId IS NULL OR t.schoolBranch.id = :branchId)
  AND t.status IN (
        org.example.data.model.TermStatus.PLANNED,
        org.example.data.model.TermStatus.ACTIVE
  )
  AND t.startDate <= :end
  AND t.endDate >= :start
""")
    List<Term> findActiveOrPlannedOverlappingSessions(
            @Param("tenantId") UUID tenantId,
            @Param("branchId") UUID branchId,
            @Param("start") Date start,
            @Param("end") Date end
    );

    long countByAcademicSession(AcademicSessionEntity academicSession);

    boolean existsByAcademicSessionAndTerm(
            AcademicSessionEntity academicSession,
            TermType term
    );


    Optional<Term> findByAdminTenantAndStatus(AdminTenant tenant, TermStatus termStatus);

//    Optional<Term> findByAdminTenantAndIsActiveTrue(AdminTenant adminTenant);
}
//    Optional<Session> findCurrentSessionForTenant(AdminTenant tenant);

