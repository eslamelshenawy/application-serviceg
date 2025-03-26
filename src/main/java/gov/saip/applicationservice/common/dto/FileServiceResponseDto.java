package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FileServiceResponseDto {


    private int statusCode;
    private String message;
    private String docId;

}
