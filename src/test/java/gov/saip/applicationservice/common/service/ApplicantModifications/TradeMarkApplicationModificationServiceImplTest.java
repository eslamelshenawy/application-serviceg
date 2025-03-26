package gov.saip.applicationservice.common.service.ApplicantModifications;

import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.applicantModifications.TradeMarkApplicationModificationDto;
import gov.saip.applicationservice.common.model.TrademarkApplicationModification;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.trademark.LkMarkType;
import gov.saip.applicationservice.common.model.trademark.LkTagTypeDesc;
import gov.saip.applicationservice.common.model.trademark.TrademarkDetail;
import gov.saip.applicationservice.common.repository.applicantModifications.TradeMarkApplicationModificationRepository;
import gov.saip.applicationservice.common.repository.trademark.LkMarkTypeRepository;
import gov.saip.applicationservice.common.repository.trademark.LkTagTypeDescRepository;
import gov.saip.applicationservice.common.service.applicantModifications.Impl.TradeMarkApplicationModificationServiceImpl;
import gov.saip.applicationservice.common.service.impl.BPMCallerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeMarkApplicationModificationServiceImplTest {
    @Mock
    private TradeMarkApplicationModificationRepository tradeMarkApplicationModificationRepository;

    @Mock

    private LkMarkTypeRepository lkMarkTypeRepository;

    @Mock
    private LkTagTypeDescRepository lkTagTypeDescRepository;
    @Mock
    private BPMCallerServiceImpl bpmCallerService;


    @InjectMocks
    @Spy
    TradeMarkApplicationModificationServiceImpl applicantModificationsService;


    @DisplayName("when checker sending A valid Modifications")
    @Test
    public void test_addApplicantModifications_when_modifications_are_Valid_PersistedModificationTracking() {
        //Arrange
        ApplicationInfo app = new ApplicationInfo(55L);
        TradeMarkApplicationModificationDto dto = new TradeMarkApplicationModificationDto();
        dto.setAppId(55L);
        dto.setNameEn("new english Name");
        dto.setNameAr("اسم عربي جديد");
        dto.setMarkDescription("وصف جديد لعلامة");
        dto.setMarkTypeId(2);
        dto.setTagTypeDesc(2);

        LkTagTypeDesc oldTagTypeDesc = new LkTagTypeDesc();
        oldTagTypeDesc.setId(1);
        LkMarkType oldMarkType = new LkMarkType();
        oldMarkType.setId(1);

        LkMarkType newMarkType = new LkMarkType();
        newMarkType.setId(2);
        LkTagTypeDesc newTagTypeDesc = new LkTagTypeDesc();
        newTagTypeDesc.setId(2);

        TrademarkDetail trademarkDetail = new TrademarkDetail();
        trademarkDetail.setId(44L);
        trademarkDetail.setApplicationId(55L);
        trademarkDetail.setNameAr("اسم عربي قديم");
        trademarkDetail.setNameEn("new english name");
        trademarkDetail.setMarkType(oldMarkType);
        trademarkDetail.setTagTypeDesc(newTagTypeDesc);
        trademarkDetail.setMarkDescription("وصف قديم لعلامة");

        TrademarkApplicationModification applicantChanges = new TrademarkApplicationModification();
        applicantChanges.setId(5L);
        applicantChanges.setApplication(app);
        applicantChanges.setNewMarkNameAr("اسم عربي جديد");
        applicantChanges.setNewMarkNameEn("new english Name");
        applicantChanges.setNewMarkDesc("وصف جديد لعلامة");
        applicantChanges.setNewMarkTypeDesc(2);
        applicantChanges.setNewMarkType(2);

        applicantChanges.setOldMarkNameAr("اسم عربي قديم");
        applicantChanges.setOldMarkNameEn("new english name");
        applicantChanges.setOldMarkDesc("وصف قديم لعلامة");
        applicantChanges.setOldMarkTypeDesc(1);
        applicantChanges.setOldMarkType(1);


        doReturn(trademarkDetail).when(applicantModificationsService).getApplicationTradeMarkDetails(app.getId());
        doReturn(applicantChanges).when(applicantModificationsService).initializeModifications(trademarkDetail, dto);
        doReturn(applicantChanges).when(applicantModificationsService).insert(applicantChanges);

        // Act
        TrademarkApplicationModification resultChanges = applicantModificationsService.addApplicantModifications(dto, app.getId());

        // assert
        assertEquals(resultChanges.getId(), 5L);
    }

    @DisplayName("checking if Auditing the checker changes acts well")
    @Test
    public void test_getAuditModificationsDifferences_when_Application_Exists_And_Changes_Persisted_Return_Changes_fileds_Map() {
        //Arrange
        ApplicationInfo app = new ApplicationInfo(55L);
        TradeMarkApplicationModificationDto dto = new TradeMarkApplicationModificationDto();
        dto.setAppId(55L);
        dto.setNameEn("new english Name");
        dto.setNameAr("اسم عربي جديد");
        dto.setMarkDescription("وصف جديد لعلامة");
        dto.setMarkTypeId(2);
        dto.setTagTypeDesc(2);

        LkTagTypeDesc oldTagTypeDesc = new LkTagTypeDesc();
        oldTagTypeDesc.setId(1);
        LkMarkType oldMarkType = new LkMarkType();
        oldMarkType.setId(1);

        LkMarkType newMarkType = new LkMarkType();
        newMarkType.setId(2);
        LkTagTypeDesc newTagTypeDesc = new LkTagTypeDesc();
        newTagTypeDesc.setId(2);

        TrademarkDetail trademarkDetail = new TrademarkDetail();
        trademarkDetail.setId(44L);
        trademarkDetail.setApplicationId(55L);
        trademarkDetail.setNameAr("اسم عربي قديم");
        trademarkDetail.setNameEn("new english name");
        trademarkDetail.setMarkType(oldMarkType);
        trademarkDetail.setTagTypeDesc(newTagTypeDesc);
        trademarkDetail.setMarkDescription("وصف قديم لعلامة");

        TrademarkApplicationModification applicantChanges = new TrademarkApplicationModification();
        applicantChanges.setId(5L);
        applicantChanges.setApplication(app);
        applicantChanges.setNewMarkNameAr("اسم عربي جديد");
        applicantChanges.setNewMarkNameEn("new english Name");
        applicantChanges.setNewMarkDesc("وصف جديد لعلامة");
        applicantChanges.setNewMarkTypeDesc(2);
        applicantChanges.setNewMarkType(2);

        applicantChanges.setOldMarkNameAr("اسم عربي قديم");
        applicantChanges.setOldMarkNameEn("new english name");
        applicantChanges.setOldMarkDesc("وصف قديم لعلامة");
        applicantChanges.setOldMarkTypeDesc(1);
        applicantChanges.setOldMarkType(1);
        List<TrademarkApplicationModification> changes = new ArrayList<>();
        changes.add(applicantChanges);

        Map<String, Object> changesAudit = new HashMap<>();
        changesAudit.put("oldNameEn", applicantChanges.getOldMarkNameEn());
        changesAudit.put("newNameEn", applicantChanges.getNewMarkNameAr());
        changesAudit.put("oldNameAr", applicantChanges.getOldMarkNameAr());
        changesAudit.put("newNameAr", applicantChanges.getNewMarkNameAr());
        changesAudit.put("oldMarkDesc", applicantChanges.getOldMarkDesc());
        changesAudit.put("newMarkDesc", applicantChanges.getNewMarkDesc());


        List<TrademarkApplicationModification> applicationModificationList = new ArrayList<>();
        applicationModificationList.add(new TrademarkApplicationModification());
        when(tradeMarkApplicationModificationRepository.getByApplicationIdOrderByModificationFilingDateDesc(anyLong())).thenReturn(Optional.of(applicationModificationList));
        RequestTasksDto requestTasksDto = new RequestTasksDto();
        requestTasksDto.setTaskId("1");
        requestTasksDto.setRowId(34l);
        requestTasksDto.setDue("2023-12-31T23:59:59.999Z");
        when(bpmCallerService.getTaskByRowId(anyLong(),anyString())).thenReturn(requestTasksDto);
        Map<String, Object> result = applicantModificationsService.getAuditModificationsDifferences(app.getId());
        // Assert
        assertNotNull(result);
    }


}
