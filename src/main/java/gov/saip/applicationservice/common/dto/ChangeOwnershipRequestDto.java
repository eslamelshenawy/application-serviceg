package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.ChangeOwnershipApplicantTypeEnum;
import gov.saip.applicationservice.common.enums.ChangeOwnershipTypeEnum;
import gov.saip.applicationservice.common.enums.DocumentTransferTypeEnum;
import gov.saip.applicationservice.common.enums.OwnerShipTransferTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class  ChangeOwnershipRequestDto extends BaseSupportServiceDto {
    private ChangeOwnershipTypeEnum changeOwnerShipType;
    private OwnerShipTransferTypeEnum documentTransferType;
    private DocumentTransferTypeEnum ownershipTransferType;
    private List<ChangeOwnershipCustomerDto> changeOwnershipCustomers= new ArrayList<>();
    private Long percentageDocPart;
    private Long customerId;
    private String customerCode;
    private int participantsCount;
    private DocumentLightDto supportDocument;
    private DocumentLightDto poaDocument;
    private DocumentLightDto waiveDocument;
    private DocumentLightDto mm5Document;
    private RequestTasksDto task;
    private String agencyRequestNumber;
    private ChangeOwnershipApplicantTypeEnum applicantType;
    private List<DocumentLightDto> changeOwnershipDocuments;
    private List<DocumentLightDto> licensesWaiveDocuments;
    private String notes;
    private CustomerSampleInfoDto agent;
    private CustomerSampleInfoDto customer;
    private CustomerSampleInfoDto oldOwner;
}
