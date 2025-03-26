package gov.saip.applicationservice.common.dto;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Role")
public class RoleDto implements Serializable {
    private String id;
    private String name;
    private String	description;
    private boolean	composite;
    private boolean	clientRole;
    private String containerId;
}
