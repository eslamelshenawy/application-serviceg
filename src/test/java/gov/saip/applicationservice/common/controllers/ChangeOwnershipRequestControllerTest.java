package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ChangeOwnershipRequestDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestDto;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyStatusCode;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import gov.saip.applicationservice.common.mapper.ChangeOwnershipRequestMapper;
import gov.saip.applicationservice.common.model.ChangeOwnershipRequest;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import gov.saip.applicationservice.common.repository.agency.TrademarkAgencyRequestRepository;
import gov.saip.applicationservice.common.service.ChangeOwnershipRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.OWNERSHIP_CHANGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ChangeOwnershipRequestControllerTest {

    @Mock
    private ChangeOwnershipRequestService changeOwnershipRequestService;

    @InjectMocks
    private ChangeOwnershipRequestController changeOwnershipRequestController;

    @Mock
    private ChangeOwnershipRequestMapper changeOwnershipRequestMapper;

    @Mock
    private TrademarkAgencyRequestRepository trademarkAgencyRequestRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testGetAllReports() {
        Long appId = 12345L;

        List<ChangeOwnershipRequestDto> requestDtos = Arrays.asList(
                new ChangeOwnershipRequestDto(),
                new ChangeOwnershipRequestDto()
        );

        List<ChangeOwnershipRequest> requestList = Arrays.asList(
                new ChangeOwnershipRequest(),
                new ChangeOwnershipRequest()
        );
        when(changeOwnershipRequestMapper.map(requestList)).thenReturn(requestDtos);
        when(changeOwnershipRequestService.getAllByApplicationId(appId, OWNERSHIP_CHANGE)).thenReturn(requestList);

        ApiResponse<List<ChangeOwnershipRequestDto>> response = changeOwnershipRequestController.getAllReports(appId);

        assertEquals(ApiResponse.ok(requestDtos), response);
    }

    @Test
    public void testGetOwnershipRequestByApplicationSupportServiceId() {
        Long id = 12345L;

        ChangeOwnershipRequestDto requestDto = new ChangeOwnershipRequestDto();

        when(changeOwnershipRequestService.getChangeOwnershipRequestByApplicationSupportServiceId(id)).thenReturn(requestDto);

        ApiResponse<ChangeOwnershipRequestDto> response = changeOwnershipRequestController.getOwnershipRequestByApplicationSupportServiceId(id);

        assertEquals(ApiResponse.ok(requestDto), response);
    }

    @Test
    public void testProcessApprovedChangeOwnershipRequest() {
        Long id = 12345L;

        ApiResponse<Void> response = changeOwnershipRequestController.processApprovedChangeOwnershipRequest(id);

        verify(changeOwnershipRequestService, times(1)).processApprovedChangeOwnershipRequest(id);
        assertEquals(ApiResponse.noContent(), response);
    }
}

