package gov.saip.applicationservice.common.dto.search;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AdvancedSearchDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String status;
    private String query;
    private Boolean isAgent;
    private String applicantName;
    private String applicationNumber;
    private String applicationTitle;
    private Long categoryId;

    private List<Long> statusesIds;

    public boolean isAllFieldsNull() {
        return (this.startDate == null) &&
                (this.endDate == null) &&
                (this.status == null || this.status.isEmpty()) &&
                (this.query == null || this.query.isEmpty()) &&
                (this.statusesIds == null || this.statusesIds.isEmpty())&&
                (this.isAgent == null) &&
                (this.applicantName == null || this.applicantName.isEmpty()) &&
                (this.applicationNumber == null || this.applicationNumber.isEmpty()) &&
                (this.applicationTitle == null || this.applicationTitle.isEmpty()) &&
                (this.categoryId == null) ;
    }
}
