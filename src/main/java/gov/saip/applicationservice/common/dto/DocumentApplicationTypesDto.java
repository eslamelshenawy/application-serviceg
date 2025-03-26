package gov.saip.applicationservice.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DocumentApplicationTypesDto implements Serializable {


    private long id;

    private List<String>  types;
    private  List<String>  extypes;


}
