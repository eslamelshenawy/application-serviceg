package gov.saip.applicationservice.common.service.consultations;

import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationDto;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationRequestDto;
import gov.saip.applicationservice.common.mapper.consultation.ExaminerConsultationMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ExaminerConsultation;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.repository.consultation.ExaminerConsultationRepository;
import gov.saip.applicationservice.common.service.Consultation.Impl.ConsultationsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExaminerConsultationServiceImplTest {
    @Mock
    private ExaminerConsultationMapper examinerConsultationMapper;
    @Mock
    private ExaminerConsultationRepository examinerConsultationRepository;



    @InjectMocks
    @Spy
    ConsultationsServiceImpl consultationsService;


    @DisplayName("when substantive checker replay on Consultation Scenario ")
    @Test
    public void test_replayConsultation_when_Replay_Parameters_Valid_Return_Persisted_Consultation_Id() {
        //Arrange
        ExaminerConsultationRequestDto dto = new ExaminerConsultationRequestDto();
        ExaminerConsultation entity = new ExaminerConsultation();
        doReturn(entity).when(consultationsService).getConsultationById(dto);
        doReturn(entity).when(consultationsService).saveConsultation(entity);
        doNothing().when(consultationsService).completeConsultation(any(),any(), any());
        // Act
         consultationsService.Replay(dto);
        //  assert
        verify(consultationsService, times(1)).completeConsultation(any(),any(), any());

    }




}
