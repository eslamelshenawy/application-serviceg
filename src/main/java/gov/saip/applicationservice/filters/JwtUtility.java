package gov.saip.applicationservice.filters;

import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtUtility implements Serializable {

    private static final long serialVersionUID = 234234523523L;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return (String) applyClaimFromToken(token, "username");
    }

    public Long getUserIdFromToken(String token) {
        String userName = (String) applyClaimFromToken(token, "username");
        if (userName.startsWith("sap") && userName.length() > 3)
            return Long.parseLong(userName.substring(3));
        return null;
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        long exp = Long.valueOf(applyClaimFromToken(token, "exp").toString());
        return new Date(exp * 1000l);
    }


    public Object applyClaimFromToken(String token, String claimName) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(claimName);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        int i = token.lastIndexOf('.');
        String withoutSignature = token.substring(0, i + 1);
        return Jwts.parser().parseClaimsJwt(withoutSignature).getBody();
    }


    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    //validate token
    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        return true;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        Collection<GrantedAuthority> authorities = new LinkedList<>();
        return authorities;
    }

    public void setBasicUserInfo(String token, HashMap<String, Object> basicUserinfo) {
        Claims claims = getAllClaimsFromToken(token);
        String userName = (String) claims.get("username");
        String familyName = (String) claims.get("family_name");
        String englishFamilyName = (String) claims.get("englishFamilyName");
        String arabicName = (String) claims.get("arabicName");
        String englishFatherName = (String) claims.get("englishFatherName");
        String englishName = (String) claims.get("englishName");
        String arabicFamilyName = (String) claims.get("arabicFamilyName");
        String arabicFatherName = (String) claims.get("arabicFatherName");
        String id = (String) claims.get("sub");
        if (userName.startsWith("sap")) {
            userName = userName.substring(3);
        }
        basicUserinfo.put("userName", userName);
        basicUserinfo.put("familyName", familyName);
        basicUserinfo.put("englishFamilyName", englishFamilyName);
        basicUserinfo.put("arabicName", arabicName);
        basicUserinfo.put("englishFatherName", englishFatherName);
        basicUserinfo.put("englishName", englishName);
        basicUserinfo.put("arabicFamilyName", arabicFamilyName);
        basicUserinfo.put("arabicFatherName", arabicFatherName);
        basicUserinfo.put("id", id);
    }





    private Set<String> resolveInternalUserRoles(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return Set.of();
        }

        Set<String> requiredAuthoritiesCodes =
                roles
                        .stream()
                        .map(Object::toString)
                        .filter(role -> role.startsWith("C05"))
                        .map(role -> role.split(":"))
                        .filter(roleArr -> roleArr.length >= 2)
                        .map(roleArr -> roleArr[1])
                        .collect(Collectors.toSet());
        return requiredAuthoritiesCodes;
    }

    public List<String> getInternalUserRoles() {
        if (Utilities.isExternal()) {
            return List.of();
        }

        String authorization = Utilities.getAuthorizationFromRequestHeaders();

        if (authorization == null || !authorization.startsWith(Constants.TOKEN_TYPE)) {
            return List.of();
        }
        String token = authorization.substring(7);
        List<String> roles = getRolesFromInternalToken(token);
        return resolveInternalUserRoles(roles).stream().toList();
    }


    public List<String> getRolesFromInternalToken(String token) {
        Object resourceAccess = applyClaimFromToken(token, "resource_access");
        if (resourceAccess == null) {
            return List.of();
        }

        Object saipinternalclient = ((LinkedHashMap) resourceAccess).get("saipinternalclient");
        if (saipinternalclient == null) {
            return List.of();
        }

        Object roles = ((LinkedHashMap) saipinternalclient).get("roles");
        if (roles == null) {
            return List.of();
        }

        return (List<String>) roles;
    }


}
