package org.example.data.repositories;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.example.data.model.AdminTenant;
import org.example.data.model.SchoolBranch;
import org.example.data.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    boolean existsByAdminTenantAndStartDateLessThanEqualAndEndDateGreaterThanEqual(AdminTenant adminTenant, Date endDate, Date startDate);

    boolean existsByAdminTenantAndStartDateAndEndDate(AdminTenant adminTenant, Date startDate, Date endDate);
    boolean existsByAdminTenantAndStartDateAndEndDateAndSchoolBranch(
            AdminTenant tenant,
            Date startDate,
            Date endDate,
            SchoolBranch branch
    );

    Optional<Session> findByAdminTenantAndSessionYearAndTerm(AdminTenant tenant, @NotBlank(message = "Session year is required") String sessionYear, @NotBlank(message = "Term is required") String term);

    Optional<Session> findByAdminTenantAndIsActiveTrue(AdminTenant tenant);

//    boolean existsByTenantIdAndIsActiveTrue(Long tenantId);

    @Modifying
    @Transactional
    @Query("UPDATE Session s SET s.isActive = false WHERE s.adminTenant.tenantId = :tenantId AND s.schoolBranch = :branch AND s.isActive = true")
    void deactivateAllForTenantAndBranch(@Param("tenantId") Long tenantId, @Param("branch") SchoolBranch branch);


    // ✅ Custom JPQL query to deactivate all active sessions for a tenant
    @Transactional
    @Modifying
    @Query("UPDATE Session s SET s.isActive = false WHERE s.adminTenant.tenantId = :tenantId AND s.isActive = true")
    void deactivateAllForTenant(@Param("tenantId") Long tenantId);
}
