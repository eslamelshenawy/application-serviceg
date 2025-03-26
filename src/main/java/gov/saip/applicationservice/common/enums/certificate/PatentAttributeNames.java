package gov.saip.applicationservice.common.enums.certificate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PatentAttributeNames {

    SUMMARY("الملخص", "Summary", "الملخص", "Summary"),
    INVENTION_BACKGROUND("خلفية اللاختراع", "Invention Background", "الوصف الكامل", "Full Description"),
    INVENTION_DESCRIPTION("الوصف العام الاختراع", "Invention Description", "الوصف الكامل", "Full Description"),
    GRAPHICS_DESCRIPTION("شرح مختصر للرسومات", "Graphics description", "الوصف الكامل", "Full Description"),
    DETAILED_DESCRIPTION("الوصف التفصيلي", "Detailed Description", "الوصف الكامل", "Full Description"),
    FULL_DESCRIPTION(null, null, "الوصف الكامل", "Full Description"),
    PROTECTED_ELEMENTS(null, null, "عناصر الحماية", "Protected Elements");
    private String attributeAr;
    private String attributeEn;
    private String mainSectionAr;
    private String mainSectionEn;


    public String getAttributeAr() {
        return attributeAr;
    }

    public String getAttributeEn() {
        return attributeEn;
    }


}

