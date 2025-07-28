import org.example.EduCompoundApplication;
import org.example.data.model.*;
import org.example.data.repositories.AdminTenantRepository;
import org.example.data.repositories.TokenRepository;
import org.example.data.repositories.UserRepository;
import org.example.teacherPackage.dto.teacherRequest.AdminInitiateTeacher;
import org.example.teacherPackage.teacherService.TeacherInvitationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertFalse;

@SpringBootTest(classes = EduCompoundApplication.class)
public class TeacherInvitationIntegrationTest {

    @Autowired
    private TeacherInvitationService teacherInvitationService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AdminTenantRepository adminTenantRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testInitiationOfTeacherRegistration() {
        // Step 1: Setup authenticated user manually
        Users adminUser = new Users();
        adminUser.setRole(Role.ADMIN);

        AdminTenant adminTenant = new AdminTenant();
        adminTenant.setTenantId(7L);
        adminTenant.setSchoolName("Bright Future High");
        adminTenant.setCreatedAt(new Date());

        // Save AdminTenant and link it to user
        adminTenantRepository.save(adminTenant);
        adminUser.setAdminTenant(adminTenant);
        adminUser.setCreatedAt(new Date());
        adminUser.setEmail("tylermacri11@gmail.com");
        userRepository.save(adminUser);

        UserPrincipal principal = new UserPrincipal(adminUser);
        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken(principal, null)
        );

        // Step 2: Create request
        AdminInitiateTeacher request = new AdminInitiateTeacher();
        request.setEmail("newteacher1@example.com");

        // Step 3: Call service method
        teacherInvitationService.initiateTeacherRegistration(request);

        // Step 4: Validate that token is saved
//        Optional<Token> tokens = tokenRepository.findByEmail("newteacher@example.com");
////        assertFalse(tokens.toString());
//        assertEquals("",tokens.get().getEmail(), "newteacher@example.com");
        Optional<Token> tokens = tokenRepository.findByEmail("newteacher1@example.com");
        assertFalse("Token list is empty", tokens.isEmpty());
    }
}
