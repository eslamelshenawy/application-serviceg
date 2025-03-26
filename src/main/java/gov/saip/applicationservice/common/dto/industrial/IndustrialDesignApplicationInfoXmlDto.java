package gov.saip.applicationservice.common.dto.industrial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "industrialDesignSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class IndustrialDesignApplicationInfoXmlDto {

    @XmlElement(name = "industrialDesign")
    private List<IndustrialDesignApplicationInfoXmlDataDto> industrialDesignApplicationInfoXmlDataDtoList;

}
