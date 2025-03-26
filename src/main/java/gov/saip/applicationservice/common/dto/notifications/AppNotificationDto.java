package gov.saip.applicationservice.common.dto.notifications;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class AppNotificationDto  {

    private Long id ;
    private int isDeleted;
    private List<String> userNames;
    private String status; // SUCCESS - WARNING - DANGER - FAILURE
    private String title;
    private String bodyAR;
    private String bodyEN;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String rowId;
    private String serviceId;
    private String routing; // PATENTS - TRADEMARKS - DESIGNS
    private Boolean isRead;
    private String serviceCode;
    private String requestId;

}
