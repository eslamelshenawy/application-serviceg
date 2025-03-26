package gov.saip.applicationservice.common.dto.patent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import gov.saip.applicationservice.common.dto.DocumentLightDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.patent.ProtectionElements;
import lombok.Data;

@Data
    public class ProtectionElementsDTO {
        private Long id;
        @JsonIgnore
        private ApplicationInfo applicationInfo;
        private Long applicationId;
        private String description;
        @JsonIgnore
        private ProtectionElements parent;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Long parentId;
        private Boolean isEnglish;
        private DocumentLightDto document;
}
