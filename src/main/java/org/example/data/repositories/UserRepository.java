package org.example.data.repositories;

import aj.org.objectweb.asm.commons.InstructionAdapter;
import org.example.data.model.AdminTenant;
import org.example.data.model.Role;
import org.example.data.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByEmail(String email);

//    List<Users> findByRole(Role role);
    List<Users> findByRoleAndAdminTenant_TenantId(Role role, UUID tenantId); // Updated to use tenantId

    boolean existsByEmail(String email);

//    InstructionAdapter findByEmailIgnoreCase(String loginId);

//    Optional<Users> findByStudentCode(String loginId);

    boolean existsByEmailAndAdminTenant(String email, AdminTenant tenant);
//    Optional<Users> findByEmail(String email);

    Optional<Users> findByStudentCode(String studentCode);

    // ✅ THIS ONE
    Optional<Users> findByEmailOrStudentCode(String email, String emailOrStudentCode);
    Optional<Users> findByEmailIgnoreCaseOrStudentCode(String email, String studentCode);

    List<Users> findAllByAdminTenantAndRole(AdminTenant tenant, Role role);

    List<Users> findAllByAdminTenant(AdminTenant tenant);

//    <T> ScopedValue<T> findByIdWithTenant(UUID userId);
    @Query("SELECT u FROM Users u JOIN FETCH u.adminTenant WHERE u.userId = :id")
    Optional<Users> findByIdWithTenant(@Param("id") UUID userId);

    boolean existsByRecoveryEmailAndAdminTenant(
            String recoveryEmail,
            AdminTenant adminTenant
    );

    Optional<Users> findByEmailAndAdminTenant(String email, AdminTenant adminTenant);
}
