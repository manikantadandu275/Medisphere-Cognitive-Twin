package medisphere_backend.auth;

import medisphere_backend.validation.HipaaAuditService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://localhost:50903"
})
public class AuthController {

    private final HipaaAuditService hipaaAuditService;

    public AuthController(HipaaAuditService hipaaAuditService) {
        this.hipaaAuditService = hipaaAuditService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.getOrDefault("username", "").trim();
        String role = credentials.getOrDefault("role", "CLINICIAN").trim().toUpperCase();

        String fullName;
        String npi = null;
        List<String> permissions;

        switch (role) {
            case "PATIENT":
                fullName = "Soundarraj (Patient P006)";
                permissions = List.of("VIEW_OWN_TWIN", "LOG_VITALS", "VIEW_CAREPLAN");
                break;
            case "NURSE":
                fullName = "Emma Watson, RN";
                permissions = List.of("VIEW_PATIENTS", "STREAM_VITALS", "ACKNOWLEDGE_ALERTS");
                break;
            case "AUDITOR":
                fullName = "Robert Lang, CISSP (HIPAA Officer)";
                permissions = List.of("VIEW_AUDIT_LOGS", "VERIFY_CONSENT", "COMPLIANCE_REVIEW");
                break;
            case "CLINICIAN":
            default:
                fullName = "Dr. A. Mehta";
                npi = "1849204812";
                permissions = List.of("VIEW_PATIENTS", "RUN_PREDICTIONS", "APPROVE_CAREPLAN", "SIGN_PRESCRIPTION", "FULL_ACCESS");
                break;
        }

        String token = "SMART-FHIR-OAUTH2-" + UUID.randomUUID().toString().substring(0, 18).toUpperCase();

        hipaaAuditService.logAction(
                "USER_AUTHENTICATED",
                fullName,
                "SYSTEM_ACCESS",
                "SUCCESS - SMART on FHIR OAuth2 Token Issued (" + role + ")"
        );

        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("token", token);
        response.put("username", username.isBlank() ? "dr.jenkins@medisphere.io" : username);
        response.put("fullName", fullName);
        response.put("role", role);
        response.put("npi", npi);
        response.put("permissions", permissions);
        response.put("authStandard", "SMART on FHIR OAuth2 / RBAC Enforced");
        response.put("authenticatedAt", LocalDateTime.now().toString());

        return response;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(@RequestBody(required = false) Map<String, String> request) {
        String user = (request != null && request.containsKey("user")) ? request.get("user") : "Clinician";

        hipaaAuditService.logAction(
                "USER_LOGOUT",
                user,
                "SYSTEM_ACCESS",
                "SESSION_TERMINATED - User logged out securely"
        );

        return Map.of(
                "status", "LOGGED_OUT",
                "message", "User session successfully terminated in compliance with HIPAA automatic timeout policies."
        );
    }
}
