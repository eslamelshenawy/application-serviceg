package gov.saip.applicationservice.common.service.opposition.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.ApplicationListDto;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.opposition.HearingSessionDto;
import gov.saip.applicationservice.common.dto.opposition.OppositionDto;
import gov.saip.applicationservice.common.enums.opposition.OppositionFinalDecision;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.model.opposition.HearingSession;
import gov.saip.applicationservice.common.model.opposition.Opposition;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationStatusRepository;
import gov.saip.applicationservice.common.repository.opposition.OppositionRepository;
import gov.saip.applicationservice.common.service.LKSupportServiceRequestStatusService;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OppositionServiceImplTest {

    @InjectMocks
    private OppositionServiceImpl oppositionService;

    @Mock
    private OppositionRepository oppositionRepository;

    @Mock
    private BPMCallerFeignClient bpmCallerFeignClient;

    @Mock
    private LkApplicationStatusRepository lkApplicationStatusRepository;

    @Mock
    LKSupportServiceRequestStatusService lkSupportServiceRequestStatusService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        oppositionService.lKSupportServiceRequestStatusService= lkSupportServiceRequestStatusService;
    }

    @Test
    @DisplayName("test applicant reply")
    void testApplicantReply_provideAppId_thenSaveAndReturnId() {
        // arrange
        Opposition opposition = new Opposition();
        opposition.setId(1L);
        opposition.setDocuments(List.of(new Document(1L)));

        HearingSession session = new HearingSession();
        session.setDate(LocalDate.now());
        session.setTime("8:00 AM ");
        session.setIsHearingSessionScheduled(false);
        opposition.setApplicantHearingSession(session);

        doNothing().when(oppositionRepository).addOppositionDocument(anyLong(),anyLong());
        doNothing().when(oppositionRepository).updateApplicantHaringSessionDate(opposition.getId(), opposition.getApplicantHearingSession().getDate(), opposition.getApplicantHearingSession().getTime(), opposition.getApplicantHearingSession().getIsHearingSessionScheduled());
        doReturn(null).when(bpmCallerFeignClient).completeUserTask(anyString(), any(CompleteTaskRequestDto.class));

        // act
        Long savedId = oppositionService.applicantReply(opposition, "1");

        // assert
        assertEquals(savedId, opposition.getId(),"saved opposition id should not be null");
    }

    @Test
    @DisplayName("test applicant reply failing")
    void testApplicantReply_provideInvalidAppId_thenThrowBusinessException() {
        // arrange
        Opposition opposition = new Opposition();
        opposition.setId(1L);



        // act
        assertThrows(BusinessException.class, () -> {
            oppositionService.applicantReply(opposition,  "1");
        }, "opposition document not provided, ");

    }


    @Test
    @DisplayName("test complainer payment ")
    void testProcessOppositionRequestPayment() {
        // arrange
        doReturn(Optional.of(new LkApplicationStatus())).when(lkApplicationStatusRepository).findByCode(anyString());
        when(lkSupportServiceRequestStatusService.findIdByCode(any())).thenReturn(any());

        // act
        oppositionService.paymentCallBackHandler(1L, new ApplicationNumberGenerationDto());

        // assert
        verify(oppositionRepository, times(1)).updateComplainerHearingSessionPaymentStatus(anyLong());
    }

    @Test
    @DisplayName("test applicant payment ")
    void testProcessApplicantHearingSessionPayment() {
        // arrange
        doNothing().when(oppositionRepository).updateApplicantPaymentStatus(anyLong());

        // act
        oppositionService.processApplicantHearingSessionPayment(1L);

        // assert
        verify(oppositionRepository, times(1)).updateApplicantPaymentStatus(anyLong());
    }


    @Test
    @DisplayName("test update complainer Hearing result ")
    void testUpdateComplainerHearingSession_whenSendResult_thenUpdateTheHearingResult() {
        // arrange
        OppositionDto opposition = new OppositionDto();
        HearingSessionDto hearingSession = new HearingSessionDto();
        hearingSession.setResult("the result");
        hearingSession.setFileURL("https://www.file.com/session-url");
        opposition.setId(1L);
        opposition.setHearingSession(hearingSession);
        doNothing().when(oppositionRepository).updateComplainerHaringSessionResult(opposition.getId(), opposition.getHearingSession().getResult(), opposition.getHearingSession().getFileURL());

        // act
        oppositionService.updateComplainerHearingSession(opposition);

        // assert
        verify(oppositionRepository, times(1)).updateComplainerHaringSessionResult(opposition.getId(), opposition.getHearingSession().getResult(), opposition.getHearingSession().getFileURL());
    }

    @Test
    @DisplayName("test update complainer Hearing date ")
    void testUpdateComplainerHearingSession_whenSendDontResult_thenUpdateTheHearingDate() {
        // arrange
        OppositionDto opposition = new OppositionDto();
        HearingSessionDto hearingSession = new HearingSessionDto();
        hearingSession.setDate(LocalDate.of(2023, 12, 12));
        hearingSession.setTime("08-30 AM");
        hearingSession.setIsHearingSessionScheduled(false);
        opposition.setId(1L);
        opposition.setHearingSession(hearingSession);
        doNothing().when(oppositionRepository).updateComplainerHaringSessionDate(opposition.getId(), opposition.getHearingSession().getDate(), opposition.getHearingSession().getTime(), opposition.getHearingSession().getIsHearingSessionScheduled());

        // act
        oppositionService.updateComplainerHearingSession(opposition);

        // assert
        verify(oppositionRepository, times(1)).updateComplainerHaringSessionDate(opposition.getId(), opposition.getHearingSession().getDate(), opposition.getHearingSession().getTime(), opposition.getHearingSession().getIsHearingSessionScheduled());
    }



    @Test
    @DisplayName("test update applicant Hearing session result ")
    void testUpdateApplicantHearingSession_whenSendResult_thenUpdateTheHearingResult() {
        // arrange
        OppositionDto opposition = new OppositionDto();
        HearingSessionDto hearingSession = new HearingSessionDto();
        hearingSession.setResult("the result");
        hearingSession.setFileURL("https://www.file.com/session-url");
        opposition.setId(1L);
        opposition.setApplicantHearingSession(hearingSession);
        doNothing().when(oppositionRepository).updateApplicantHaringSessionResult(opposition.getId(), opposition.getApplicantHearingSession().getResult(), opposition.getApplicantHearingSession().getFileURL());

        // act
        oppositionService.updateApplicantHearingSession(opposition);

        // assert
        verify(oppositionRepository, times(1)).updateApplicantHaringSessionResult(opposition.getId(), opposition.getApplicantHearingSession().getResult(), opposition.getApplicantHearingSession().getFileURL());
    }

    @Test
    @DisplayName("test update complainer Hearing date ")
    void testUpdateApplicantHearingSession_whenSendDontResult_thenUpdateTheHearingDate() {
        // arrange
        OppositionDto opposition = new OppositionDto();
        HearingSessionDto hearingSession = new HearingSessionDto();
        hearingSession.setDate(LocalDate.of(2023, 12, 12));
        hearingSession.setTime("08-30 AM");
        hearingSession.setIsHearingSessionScheduled(false);
        opposition.setId(1L);
        opposition.setApplicantHearingSession(hearingSession);
        doNothing().when(oppositionRepository).updateApplicantHaringSessionDate(opposition.getId(), opposition.getApplicantHearingSession().getDate(), opposition.getApplicantHearingSession().getTime(), opposition.getApplicantHearingSession().getIsHearingSessionScheduled());

        // act
        oppositionService.updateApplicantHearingSession(opposition);

        // assert
        verify(oppositionRepository, times(1)).updateApplicantHaringSessionDate(opposition.getId(), opposition.getApplicantHearingSession().getDate(), opposition.getApplicantHearingSession().getTime(), opposition.getApplicantHearingSession().getIsHearingSessionScheduled());
    }


    @Test
    @DisplayName("test update applicant examiner  ")
    void testUpdateApplicantExaminerNotes_whenSendApplicantExaminer_thenUpdateExaminerNotes() {
        // arrange
        OppositionDto opposition = new OppositionDto();
        HearingSessionDto hearingSession = new HearingSessionDto();
        opposition.setId(1L);
        opposition.setApplicantExaminerNotes("applicant examiner notes");
        doNothing().when(oppositionRepository).updateApplicantExaminerNotes(opposition.getId(), opposition.getApplicantExaminerNotes());

        // act
        oppositionService.updateApplicantExaminerNotes(opposition);

        // assert
        verify(oppositionRepository, times(1)).updateApplicantExaminerNotes(opposition.getId(), opposition.getApplicantExaminerNotes());
    }



    @Test
    @DisplayName("test final decision examiner  ")
    void testExaminerFinalDecision_whenExaminerSendHisFinalDecision_thenUpdateFinalDecision() {
        // arrange
        OppositionDto opposition = new OppositionDto();
        HearingSessionDto hearingSession = new HearingSessionDto();
        opposition.setId(1L);
        opposition.isAccepted();
        opposition.setFinalNotes("final notes");
        doNothing().when(oppositionRepository).examinerFinalDecision(opposition.getId(), opposition.isAccepted() ? OppositionFinalDecision.ACCEPTED : OppositionFinalDecision.REJECTED, opposition.getFinalNotes());

        // act
        oppositionService.examinerFinalDecision(opposition);

        // assert
        verify(oppositionRepository, times(1)).examinerFinalDecision(opposition.getId(), opposition.isAccepted() ? OppositionFinalDecision.ACCEPTED : OppositionFinalDecision.REJECTED, opposition.getFinalNotes());
    }

    @Test
    @DisplayName("test final decision examiner  ")
    void testHeadExaminerNotesToExaminer_whenHeadExaminerSendValidNotes_thenUpdateNotesColumn() {
        // arrange
        Opposition opposition = new Opposition();
        HearingSessionDto hearingSession = new HearingSessionDto();
        opposition.setId(1L);
        opposition.setHeadExaminerNotesToExaminer("notes");
        doNothing().when(oppositionRepository).headExaminerNotesToExaminer(opposition.getId(), opposition.getHeadExaminerNotesToExaminer());

        // act
        oppositionService.headExaminerNotesToExaminer(opposition);

        // assert
        verify(oppositionRepository, times(1)).headExaminerNotesToExaminer(opposition.getId(), opposition.getHeadExaminerNotesToExaminer());
    }

    @Test
    @DisplayName("test head examiner final confirmation ")
    void testConfirmFinalDecisionFromHeadOfExaminer_ifUserConfirm_thenUpdateStatus() {
        // arrange
        Long oppositionId = 1L;
        LkApplicationStatus status = new LkApplicationStatus();
        doNothing().when(oppositionRepository).updateHeadExaminerConfirmation(oppositionId);
        when(oppositionRepository.getFinalDecisionById(oppositionId)).thenReturn(OppositionFinalDecision.ACCEPTED);
        when(lkApplicationStatusRepository.findByCode(anyString())).thenReturn(Optional.of(status));
        doNothing().when(oppositionRepository).updateApplicationStatus(oppositionId, status);
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1l);
        applicationInfo.setCategory(new LkApplicationCategory());
        when(oppositionRepository.getApplicationByServiceId(anyLong())).thenReturn(applicationInfo);
        when(lkSupportServiceRequestStatusService.findIdByCode(any())).thenReturn(1);
        oppositionService.confirmFinalDecisionFromHeadOfExaminer(oppositionId);

        // assert
        verify(oppositionRepository, times(1)).updateHeadExaminerConfirmation(oppositionId);
    }

}