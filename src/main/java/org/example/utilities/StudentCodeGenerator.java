package org.example.utilities;

import org.example.data.model.AdminTenant;
import org.example.data.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class StudentCodeGenerator {

    private static final Random random = new Random();
    private static final int MAX_ATTEMPTS = 10;

    public static String generateUniqueStudentCode(String className, StudentRepository studentRepository, AdminTenant tenant) {
        String prefix = className.trim().toUpperCase().replaceAll("\\s+", "");

        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            int number = 1000 + random.nextInt(9000);
            String studentCode = prefix + "-" + number;

            boolean exists = studentRepository.existsByStudentCodeAndAdminTenant(studentCode, tenant);
            if (!exists) return studentCode;
        }

        throw new IllegalStateException("Unable to generate unique student code after " + MAX_ATTEMPTS + " attempts.");
    }
}
