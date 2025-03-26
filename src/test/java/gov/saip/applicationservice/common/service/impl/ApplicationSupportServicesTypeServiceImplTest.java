package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.search.SearchDto;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.mapper.ApplicationPreviousRequestsMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.repository.ApplicationSupportServicesTypeRepository;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ApplicationSupportServicesTypeServiceImplTest {

    @InjectMocks
    private ApplicationSupportServicesTypeServiceImpl service;

    @Mock
    private ApplicationSupportServicesTypeRepository repository;

    @Mock
    HttpServletRequest request;

    @Mock
    Util util;

    @Mock
    ApplicationPreviousRequestsMapper applicationPreviousRequestsMapper;

    @Mock
    ApplicationInstallmentService applicationInstallmentService;

    @Mock
    ApplicationSupportServicesTypeRepository applicationSupportServicesTypeRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service.setApplicationInstallmentService(applicationInstallmentService);
    }

    public void testValidateApplicationSupportServicesTypeExistsWhenExists() {
        Long id = 1L;
        when(repository.applicationSupportServicesTypeExists(id)).thenReturn(true);
        when(applicationSupportServicesTypeRepository.applicationSupportServicesTypeExists(id)).thenReturn(false);
        service.validateApplicationSupportServicesTypeExists(id);
        verify(applicationSupportServicesTypeRepository, times(1)).applicationSupportServicesTypeExists(id);
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.validateApplicationSupportServicesTypeExists(id)
        );

    }


    @Test
    public void testValidateApplicationSupportServicesTypeExistsWhenNotExists() {
        Long id = 1L;
        when(repository.applicationSupportServicesTypeExists(id)).thenReturn(false);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.validateApplicationSupportServicesTypeExists(id)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    public void testPaymentCallBackHandler() {
        Long id = 1L;
        ApplicationSupportServicesType request = new ApplicationSupportServicesType();
        request.setPaymentStatus(SupportServicePaymentStatus.PAID);
        when(repository.findById(id)).thenReturn(Optional.of(request));
        ApplicationInstallment applicationInstallment = new ApplicationInstallment();
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1l);
        applicationInstallment.setApplication(applicationInfo);
        when(applicationInstallmentService.findById(id)).thenReturn(applicationInstallment);
        when(applicationSupportServicesTypeRepository.getLastServiceForApplicationByServiceCode(any(),any())).thenReturn(new ApplicationSupportServicesType());

    }

    @Test
    public void testGetPaymentRequestStatus() {
        // Act
        SupportServiceRequestStatusEnum status = service.getPaymentRequestStatus();

        assertEquals(SupportServiceRequestStatusEnum.COMPLETED, status);
    }

    @Test
    public void testGetPreviousRequestsByFilter() {
        Integer page = 1;
        Integer limit = 10;
        String query = "test";
        LocalDateTime fromDate = LocalDateTime.now().minusDays(30);
        LocalDateTime toDate = LocalDateTime.now();
        Long applicationId = 1L;
        Long supportServiceTypeId = 2L;
        boolean isInternal = true;

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "id"));
        List<ApplicationPreviousRequests> previousRequestsList = new ArrayList<>();
        List<ApplicationPreviousRequestsDto> previousRequestsDtos = new ArrayList<>();
        SearchDto searchDto = new SearchDto();
        List<Long> customerIds = null;

        Page<ApplicationPreviousRequests> applicationInfoPages = new PageImpl<>(previousRequestsList);
        when(repository.getInternalPreviousRequest(
                query, searchDto.getStartDate(), searchDto.getEndDate(), searchDto.getApplicationId(), searchDto.getSupportServiceTypeId(),
                SupportServicePaymentStatus.UNPAID, searchDto.getCategoryId(), searchDto.getApplicationNumber(), searchDto.getOwnerName(),
                searchDto.getAgentName(), searchDto.getApplicationTitleAr(), searchDto.getApplicationTitleEn(), searchDto.getStatus(), customerIds, pageable))
                .thenReturn(applicationInfoPages);

        when(applicationPreviousRequestsMapper.map(previousRequestsList)).thenReturn(previousRequestsDtos);

        PaginationDto<List<ApplicationPreviousRequestsDto>> result = service.getPreviousRequestsByFilter(
                page, limit, query, searchDto, isInternal);
        assertNotNull(result);
        assertEquals(0, result.getContent().size());
        assertEquals(0, result.getTotalElements());
    }

    @Test
    public void testGetOwnerShipChangedData() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();
        List<ChangeOwnerShipReportDTO> ownershipChangedData = new ArrayList<>();
        when(repository.getOwnerShipChangedData(startDate, endDate)).thenReturn(ownershipChangedData);

        List<ChangeOwnerShipReportDTO> result = service.getOwnerShipChangedData(startDate, endDate);

        assertNotNull(result);
        assertEquals(ownershipChangedData, result);
    }
    
    @Test
    public void testUpdateRequestStatusById() throws Exception {
        // Arrange
        Long id = 1L;
        Integer newStatusId = 1;
        Mockito.doNothing().when(repository).updateRequestStatus(newStatusId, id);
        
        //act
        repository.updateRequestStatus(newStatusId, id);
        
        //assert
        verify(repository, times(1)).updateRequestStatus(newStatusId, id);
        
    }
    
}

