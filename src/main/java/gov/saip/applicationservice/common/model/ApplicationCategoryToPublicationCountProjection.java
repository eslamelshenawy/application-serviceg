package gov.saip.applicationservice.common.model;

public record ApplicationCategoryToPublicationCountProjection(
        Long applicationCategoryId,
        String applicationCategorySaipCode,
        String applicationCategoryDescEn,
        String applicationCategoryDescAr,
        Long count) {
}
