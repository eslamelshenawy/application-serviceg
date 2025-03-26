package gov.saip.applicationservice.util.objectmother;

import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.PublicationIssue;

public class PublicationIssueTestFactory {

    public static final long ISSUE_NUMBER = 1L;
    public static final String NAME_EN = "Issue 1";
    public static final String NAME_AR = "العدد 1";

    public static PublicationIssue.PublicationIssueBuilder aDefaultPublicationIssue(LkApplicationCategory applicationCategory) {
        return PublicationIssue.builder()
                .issueNumber(ISSUE_NUMBER)
                .lkApplicationCategory(applicationCategory)
                .nameEn(NAME_EN)
                .nameAr(NAME_AR);
    }
}
