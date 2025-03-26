package gov.saip.applicationservice.common.controllers.trademark;


import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.trademark.PublicationApplicationTrademarkDetailDto;
import gov.saip.applicationservice.common.dto.trademark.TrademarkDetailDto;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing trademark details.
 *
 * This class provides endpoints for creating, retrieving, and updating trademark details.
 * The trademark details are represented by instances of the {@link TrademarkDetailDto} class.
 *
 * <p>The controller class uses a {@link TrademarkDetailService} to interact with the data store.
 * The service is injected into the class using the {@link RequiredArgsConstructor} annotation.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * TrademarkDetailController trademarkDetailController = new TrademarkDetailController();
 * ResponseEntity<TrademarkDetailDto> response = trademarkDetailController.findDtoById(1L);
 * }</pre>
 *
 * @see TrademarkDetailDto
 * @see TrademarkDetailService
 */
@RestController()
@RequestMapping("pb/trademark-detail")
@RequiredArgsConstructor
@Slf4j
public class PublicTrademarkDetailController {
    
    private final TrademarkDetailService trademarkDetailService;
    
    
    /**
     * Retrieves publication trademark details for the specified application ID.
     *
     * @param applicationId the ID of the application
     * @return an {@link ApiResponse} containing the application trademark detail DTO
     */
    @GetMapping("/publication/{applicationId}")
    public ApiResponse<PublicationApplicationTrademarkDetailDto> getPublicationTradeMarkDetails(
            @PathVariable(value = "applicationId") Long applicationId,
            @RequestParam(value = "applicationPublicationId", required = false) Long applicationPublicationId){
        PublicationApplicationTrademarkDetailDto dto = trademarkDetailService.getPublicationTradeMarkDetails(applicationId, applicationPublicationId);
        return ApiResponse.ok(dto);
    }
    
    
    
}
