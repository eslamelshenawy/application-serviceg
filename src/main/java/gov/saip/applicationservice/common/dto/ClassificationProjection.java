package gov.saip.applicationservice.common.dto;

public interface ClassificationProjection {
    
    Long getId();
    
    String getCode();
    
    String getNameAr();
    
    String getNameEn();

    Long getVersionId();

    String getVersionCode();

    String getVersionNameAr();

    String getVersionNameEn();

    Long getVersionCategoryId();
}
