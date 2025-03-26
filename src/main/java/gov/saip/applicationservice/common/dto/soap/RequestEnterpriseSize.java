package gov.saip.applicationservice.common.dto.soap;

import lombok.Data;

@Data
public class RequestEnterpriseSize {

    private String crNumber;
    private FileNumberStructure fileNumber;
    private String nationalNumber;

}
