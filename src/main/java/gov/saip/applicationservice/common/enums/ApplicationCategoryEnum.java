package gov.saip.applicationservice.common.enums;

import gov.saip.applicationservice.common.model.LkApplicationCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationCategoryEnum {
    PATENT(1L, "PATENT", "PATENT", "patent_application_process"),
    INDUSTRIAL_DESIGN(2L, "INDUSTRIAL_DESIGN", "INDUSTRIAL_DESIGN", "industrial_design_application_process"),
    TRADEMARK(5L, "TRADEMARK", "TRADEMARK", "trademark_application_process"),
    INTEGRATED_CIRCUITS(4L, "INTEGRATED_CIRCUITS", "INTEGRATED_CIRCUITS", "integrated_circuits_application_process"),
    PLANT_VARIETIES(3L, "PLANT_VARIETIES", "PLANT_VARIETIES", "plant_varieties_application_process"),
    COPYRIGHT(0L, null, null, null),
    GEOGRAPHICAL_INDICATION(0L, null, null, null),
    PUBLICATION(0L, null, null, null),
    CASE_AND_COMPLAIN(0L, null, null, null),
    LICENSE(0L, null, null, null);

    private Long id;
    private String processTypeCode;
    private String saipCode;
    private String processName;

    public LkApplicationCategory getLkApplicationCategory() {
        return new LkApplicationCategory(id, saipCode);
    }
}
