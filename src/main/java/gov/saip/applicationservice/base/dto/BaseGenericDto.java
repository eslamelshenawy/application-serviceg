package gov.saip.applicationservice.base.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class BaseGenericDto<ID extends Serializable> extends BaseDto<ID> {
}
