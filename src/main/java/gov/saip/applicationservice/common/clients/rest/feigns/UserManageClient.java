package gov.saip.applicationservice.common.clients.rest.feigns;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.KeycloakUserDto;
import gov.saip.applicationservice.common.dto.RoleDto;
import gov.saip.applicationservice.report.dto.UserLightDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "user-administration-service", url = "${client.feign.user.manage}")
public interface UserManageClient {
    @PostMapping (value = "/internal-calling/v1/user")
    public void createUser(@RequestBody @Valid KeycloakUserDto dto,
                           @RequestHeader(name="loginType") String loginType,
                           @RequestHeader("Cookie") String cookie);
    @GetMapping (value = "/internal-calling/v1/user/{keycloakId}")
    public List<RoleDto> getInternalUserClientRoles(@PathVariable (name="keycloakId") String keycloakId,
                                                    @RequestHeader(name="loginType") String loginType);

    @GetMapping (value = "/internal-calling/v1/user/info/{id}")
    public Object getUserInfo(@PathVariable(name = "id") String id,
                           @RequestHeader(name="loginType") String loginType);
    @GetMapping(value = "/internal-calling/full-names")
    public ApiResponse<List<String>> getFullNameByUsernames(@RequestParam (value="usernames") List<String> usernames);

    @GetMapping("/internal-calling/checker/task-roles")
    ApiResponse<List<String>> getUserRoles();


    @GetMapping(value = "/internal-calling/user/full-details")
    ApiResponse<UserLightDto> getUserDetails(@RequestParam (value="username") String username);


}
