package gov.saip.applicationservice.common.service.trademark.impl;


import gov.saip.applicationservice.common.dto.ApplicationDocumentLightDto;
import gov.saip.applicationservice.common.dto.ApplicationSubstantiveExaminationRetrieveDto;
import gov.saip.applicationservice.common.dto.trademark.*;
import gov.saip.applicationservice.common.mapper.trademark.TrademarkDetailMapper;
import gov.saip.applicationservice.common.model.trademark.LkMarkType;
import gov.saip.applicationservice.common.model.trademark.LkTagLanguage;
import gov.saip.applicationservice.common.model.trademark.LkTagTypeDesc;
import gov.saip.applicationservice.common.model.trademark.TrademarkDetail;
import gov.saip.applicationservice.common.projection.TradeMarkInfo;
import gov.saip.applicationservice.common.repository.trademark.TrademarkDetailRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationSubClassificationService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.trademark.LkMarkTypeService;
import gov.saip.applicationservice.common.service.trademark.LkTagLanguageService;
import gov.saip.applicationservice.common.service.trademark.LkTagTypeDescService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.generics.ApplicationInfoGenericService;
import gov.saip.applicationservice.util.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TrademarkDetailServiceImplTest {

    @InjectMocks
    @Spy
    private TrademarkDetailServiceImpl trademarkDetailServiceImpl;
    @Mock
    private TrademarkDetailRepository trademarkDetailRepository;
    @Mock
    private TrademarkDetailMapper trademarkDetailMapper;
    @Mock
    private ApplicationInfoService applicationInfoService;
    @Mock
    private DocumentsService documentsService;
    @Mock
    private ApplicationSubClassificationService applicationSubClassificationService;
    @Mock
    private LkMarkTypeService lkMarkTypeService;
    @Mock
    private LkTagTypeDescService lkTagTypeDescService;

    @Mock
    private LkTagLanguageService lkTagLanguageService;
    @Autowired
    @Spy
    private ApplicationInfoGenericService applicationInfoGenericService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreate() {
        TrademarkDetailReqDto req = new TrademarkDetailReqDto();
        req.setId(1l);
        TrademarkDetail entity = new TrademarkDetail();
        entity.setId(1l);
        Long applicationId = 1L;
        Optional<TrademarkDetail> trademarkDetailOptional = Optional.of(entity);
        when(trademarkDetailRepository.findByApplicationId(applicationId)).thenReturn(trademarkDetailOptional);
        when(trademarkDetailMapper.mapDetReq(req, trademarkDetailOptional.get())).thenReturn(entity);
        when(lkMarkTypeService.getReferenceById(req.getMarkTypeId())).thenReturn(new LkMarkType());
        when(lkTagTypeDescService.getReferenceById(req.getTagTypeDescId())).thenReturn(new LkTagTypeDesc());
        when(lkTagLanguageService.getReferenceById(req.getTagLanguageId())).thenReturn(new LkTagLanguage());
        Mockito.doReturn(entity).when(trademarkDetailServiceImpl).insert(entity);
        Long id = trademarkDetailServiceImpl.create(req, applicationId);
        assertEquals(entity.getId(), id);
    }

    @Test
    public void testCreateEntityNotExist() {
        TrademarkDetailReqDto req = new TrademarkDetailReqDto();
        req.setId(1l);
        Long applicationId = 1L;
        Optional<TrademarkDetail> trademarkDetailOptional = Optional.empty();
        TrademarkDetail entity = new TrademarkDetail();
        when(trademarkDetailRepository.findByApplicationId(applicationId)).thenReturn(trademarkDetailOptional);
        when(trademarkDetailMapper.mapDetReq(req)).thenReturn(entity);
        when(lkMarkTypeService.getReferenceById(req.getMarkTypeId())).thenReturn(new LkMarkType());
        when(lkTagTypeDescService.getReferenceById(req.getTagTypeDescId())).thenReturn(new LkTagTypeDesc());
        when(lkTagLanguageService.getReferenceById(req.getTagLanguageId())).thenReturn(new LkTagLanguage());
        Mockito.doReturn(entity).when(trademarkDetailServiceImpl).insert(entity);
        Long id = trademarkDetailServiceImpl.create(req, applicationId);
        assertEquals(entity.getId(), id);
    }

    @Test
    public void testFindByApplicationId() {
        Long applicationId = 1L;
        TrademarkDetail entity = new TrademarkDetail();
        when(trademarkDetailRepository.findByApplicationId(applicationId)).thenReturn(Optional.of(entity));
        TrademarkDetail result = trademarkDetailServiceImpl.findByApplicationId(applicationId);
        assertEquals(entity, result);
    }
    @Test
    public void testFindDtoByApplicationId() {
        Long applicationId = 1L;
        TrademarkDetail entity = new TrademarkDetail();
        when(trademarkDetailRepository.findByApplicationId(applicationId)).thenReturn(Optional.of(entity));
        TrademarkDetailDto trademarkDetailDto = new TrademarkDetailDto();
        when(trademarkDetailMapper.map(entity)).thenReturn(trademarkDetailDto);
        TrademarkDetailDto result = trademarkDetailServiceImpl.findDtoByApplicationId(applicationId);
        assertEquals(trademarkDetailDto, result);
    }

    @Test
    public void testFindDtoById() {
        Long id = 1L;
        TrademarkDetail entity = new TrademarkDetail();
        TrademarkDetailDto trademarkDetailDto = new TrademarkDetailDto();
        trademarkDetailDto.setNameEn("name");
        Mockito.doReturn(entity).when(trademarkDetailServiceImpl).findById(id);
        when(trademarkDetailMapper.map(entity)).thenReturn(trademarkDetailDto);
        TrademarkDetailDto result = trademarkDetailServiceImpl.findDtoById(id);
        assertEquals("name", result.getNameEn());
    }

    @Test
    public void testGetTradeMarkApplicaionInfo() {
        String applicantCode = "ABC";
        List<TradeMarkInfo> tradeMarkInfoList = new ArrayList<>();
        when(trademarkDetailRepository.getTradeMarkApplicationInfo(applicantCode)).thenReturn(tradeMarkInfoList);
        List<TradeMarkInfo> result = trademarkDetailServiceImpl.getTradeMarKApplicaionInfo(applicantCode);
        assertEquals(tradeMarkInfoList, result);
    }

    @Test
    public void testGetTradeMarkByApplicationId() {
        Long id = 1L;
        TradeMarkInfo tradeMarkInfo = getTradeMarkInfo();
        when(trademarkDetailRepository.getTradeMarkByApplicationId(id)).thenReturn(tradeMarkInfo);
        TradeMarkInfo result = trademarkDetailServiceImpl.getTradeMarkByApplicationId(id);
        assertEquals(tradeMarkInfo, result);
    }

    @Test
    public void testGetApplicationTrademarkDetails() {
        Long applicationId = 1L;
        TrademarkDetail entity = new TrademarkDetail();
        ApplicationTrademarkDetailDto applicationTrademarkDetailDto = new ApplicationTrademarkDetailDto();
        applicationTrademarkDetailDto.setNameEn("name");
        applicationTrademarkDetailDto.setImageLink("image");
        ApplicationDocumentLightDto applicationDocumentLightDto= new ApplicationDocumentLightDto();
        applicationDocumentLightDto.setFileReviewUrl("image");
        ApplicationSubstantiveExaminationRetrieveDto applicationSubstantiveExaminationRetrieveDto = new ApplicationSubstantiveExaminationRetrieveDto();
        applicationSubstantiveExaminationRetrieveDto.setId(1);
        when(trademarkDetailRepository.findByApplicationId(applicationId)).thenReturn(Optional.of(entity));
        when(trademarkDetailMapper.mapApplicationDet(entity)).thenReturn(applicationTrademarkDetailDto);
        when(applicationInfoService.getApplicationSubstantiveExamination(applicationId)).thenReturn(applicationSubstantiveExaminationRetrieveDto);
        when(documentsService.getDocumentByApplicationIdAndType(applicationId)).thenReturn(applicationDocumentLightDto);
        when(documentsService.findTrademarkLatestDocumentByApplicationIdAndDocumentType(any())).thenReturn(applicationDocumentLightDto);
        ApplicationTrademarkDetailDto result = trademarkDetailServiceImpl.getApplicationTrademarkDetails(applicationId);
        assertEquals(applicationTrademarkDetailDto, result);
        assertEquals("name", result.getNameEn());
        assertEquals(1, result.getApplication().getId());
    }

    private TrademarkApplicationInfoXmlDto convertToApplicationInfoXmlDto(ByteArrayResource file) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(TrademarkApplicationInfoXmlDto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (TrademarkApplicationInfoXmlDto) jaxbUnmarshaller
                .unmarshal(file.getInputStream());
    }

    @Test
    public void testGetApplicationInfoXml() throws JAXBException, IOException {
        // Arrange
        Long applicationId = 1L;
        TrademarkApplicationInfoXmlDataDto dataDto = TrademarkApplicationInfoXmlDataDto.builder()
                .email("mahmoud.aboelsoud@wscdev.net")
                .build();
        TrademarkApplicationInfoXmlDto expectedDto = TrademarkApplicationInfoXmlDto.builder()
                .trademarkApplicationInfoXmlDataDtoList(Collections.singletonList(dataDto))
                .build();
        when(trademarkDetailRepository.getApplicationInfoXmlDataDto(applicationId))
                .thenReturn(Optional.ofNullable(dataDto));
        // Act
        ByteArrayResource file = trademarkDetailServiceImpl.getApplicationInfoXml(applicationId);
        TrademarkApplicationInfoXmlDto actualDto = convertToApplicationInfoXmlDto(file);
        // Assert
        Assertions.assertThat(file).isNotNull();
        Assertions.assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    public void testGetApplicationInfoXml_applicationIdNotFound() {
        // Arrange
        Long applicationId = 1L;
        when(trademarkDetailRepository.getApplicationInfoXmlDataDto(applicationId)).thenReturn(Optional.empty());
        // Act & Assert
        BusinessException ex = assertThrows(BusinessException.class,
                () -> trademarkDetailServiceImpl.getApplicationInfoXml(applicationId));
        Assertions.assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND);
    }

    private TradeMarkInfo getTradeMarkInfo() {
        return new TradeMarkInfo() {
            @Override
            public Long getAppId() {
                return null;
            }

            @Override
            public String getAppTitleAr() {
                return null;
            }

            @Override
            public String getAppTitleEn() {
                return null;
            }

            @Override
            public String getStatusAr() {
                return null;
            }

            @Override
            public String getApplicationNumber() {
                return null;
            }

            @Override
            public String getExaminerGrantCondition() {
                return null;
            }

            @Override
            public String getStatusEn() {
                return null;
            }

            @Override
            public String getClassificationAr() {
                return null;
            }

            @Override
            public String getClassificationEn() {
                return null;
            }

            @Override
            public String getTmWorkMark() {
                return null;
            }

            @Override
            public String getMarkDescription() {
                return null;
            }

            @Override
            public String getMarkClaimingColor() {
                return null;
            }

            @Override
            public String getTmTypeAr() {
                return null;
            }

            @Override
            public String getTmTypeEn() {
                return null;
            }

            @Override
            public String getCustomerCode() {
                return null;
            }
        };
    }
}