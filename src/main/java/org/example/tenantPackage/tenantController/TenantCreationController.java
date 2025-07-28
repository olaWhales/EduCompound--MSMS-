package org.example.tenantPackage.tenantController;

import lombok.RequiredArgsConstructor;
import org.example.teacherPackage.dto.teacherRequest.TenantCreationRequest;
import org.example.tenantPackage.tenantService.TenantRegistration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/superadmin/tenants")
@RequiredArgsConstructor
public class TenantCreationController {

    private final TenantRegistration tenantRegistration;

    @PostMapping("/registration")
    public ResponseEntity<?> tenantCreation(@RequestBody TenantCreationRequest tenantCreationRequest){
        try{
            return new ResponseEntity<>(tenantRegistration.createTenant(tenantCreationRequest), HttpStatus.CREATED);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
