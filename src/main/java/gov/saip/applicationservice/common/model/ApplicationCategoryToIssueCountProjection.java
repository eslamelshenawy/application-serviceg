package gov.saip.applicationservice.common.model;

public record ApplicationCategoryToIssueCountProjection(
        Long applicationCategoryId,
        String applicationCategorySaipCode,
        String applicationCategoryDescEn,
        String applicationCategoryDescAr,
        Long count) {
}
