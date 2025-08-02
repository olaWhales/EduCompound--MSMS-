package org.example.studentBulkResultEntryPackage.services;

import java.util.UUID;

public class UUIDUtil {

    public static UUID validateAndConvert(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid UUID format: " + id);
        }
    }
}
