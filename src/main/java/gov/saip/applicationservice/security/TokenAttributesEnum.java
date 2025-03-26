package gov.saip.applicationservice.security;

public enum TokenAttributesEnum {
  AUTHORITIES("clientRoles"),

  USER_EMAIL("email"),
  USER_SYSTEM_ID("sub"),
  USER_LEVEL_SYSTEM_ID("ulid"),

  USER_NAME("username"),
  PREFERRED_USER_NAME("preferred_username"),
  REALM_ID("rid"),
  CONSUMER_KEY("key"),
  CLIENT_ID("azp"),

  TOKEN_EXPIRY_DATE("exp"),
  TOKEN_CREATION_DATE("iat"),

  REALM_ACCESS("realm_access"),
  RESOURCE_ACCESS("resource_access"),
  SAIP_INTERNAL_CLIENT("saipinternalclient"),
  ROLES("roles");

  public final String value;

  TokenAttributesEnum(final String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
