package gov.saip.applicationservice.common.dto;

import lombok.Data;

@Data
public class KeycloakUserDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private boolean emailVerified;
}
