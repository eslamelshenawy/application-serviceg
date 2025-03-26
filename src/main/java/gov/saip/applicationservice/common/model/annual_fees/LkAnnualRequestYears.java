package gov.saip.applicationservice.common.model.annual_fees;

import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lk_annual_request_years")
@Setter
@Getter
@NoArgsConstructor
public class LkAnnualRequestYears extends BaseLkEntity<Long> {

    private int yearsCount;
}
