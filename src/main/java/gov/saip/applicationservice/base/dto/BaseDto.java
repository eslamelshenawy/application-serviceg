package gov.saip.applicationservice.base.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class BaseDto<ID extends Serializable> {

    private ID id;

    private int isDeleted;

}
