package gov.saip.applicationservice.base.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * light dtos used in cases like single select and multi select where front end just need
 * @param <ID>
 */
@Setter
@Getter
public class BaseLightDto<ID  extends Serializable> extends BaseDto<ID> {
    private String nameAr;
    private String nameEn;
}
