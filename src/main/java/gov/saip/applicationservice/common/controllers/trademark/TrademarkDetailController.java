package gov.saip.applicationservice.common.controllers.trademark;


import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationInfoRequestLightDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.trademark.*;
import gov.saip.applicationservice.common.enums.ThirdPartyRoute;
import gov.saip.applicationservice.common.projection.TradeMarkInfo;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.TrademarkApplicationFacade;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import gov.saip.applicationservice.util.DownloadFileUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
@RequestMapping({"kc/trademark-detail", "/internal-calling/trademark-detail"})
@RequiredArgsConstructor
@Slf4j
public class TrademarkDetailController {

    private final ApplicationInfoService applicationInfoService;
    private final TrademarkDetailService trademarkDetailService;
    private final TrademarkApplicationFacade trademarkApplicationFacade;

    /**
     * Creates a new trademark detail for the specified application ID.
     *
     * @param req the trademark detail request
     * @param id the ID of the application
     * @return an {@link ApiResponse} containing the created trademark detail
     */
    @PostMapping("/application/{id}")
    public ApiResponse<Object> create(@Valid @RequestBody TrademarkDetailReqDto req, @PathVariable Long id) {
        return ApiResponse.created(trademarkDetailService.create(req, id));
    }

    /**
     * Update trademark detail for the specified application ID.
     *
     * @param req the trademark detail request
     * @param id the ID of the application
     * @return an {@link ApiResponse} containing the number of affected rows
     */
    @PutMapping("/application/{id}")
    public ApiResponse<Object> update(@Valid @RequestBody TradeMarkLightDto req, @PathVariable Long id) {
        return ApiResponse.created(trademarkDetailService.update(req, id));
    }

    /**
     * Retrieves a trademark detail with the specified ID.
     *
     * @param id the ID of the trademark detail to retrieve
     * @return a {@link ResponseEntity} containing the trademark detail DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrademarkDetailDto> findDtoById(@PathVariable Long id) {
        TrademarkDetailDto dto = trademarkDetailService.findDtoById(id);
        return ResponseEntity.ok(dto);

    }

    /**
     * Retrieves a trademark detail with the specified application ID.
     *
     * @param id the ID of the application
     * @return a {@link ResponseEntity} containing the trademark detail DTO
     */
    @GetMapping("application/{id}")
    public ResponseEntity<TrademarkDetailDto> findDtoByApplicationId(@PathVariable Long id) {
        return ResponseEntity.ok(trademarkDetailService.findDtoByApplicationId(id));

    }


    @GetMapping("/suspcion/application/{applicationNumber}")
    public ApiResponse<Object> findDtoBySucpionApplicationId(@PathVariable (name="applicationNumber")String applicationNumber) {
        return ApiResponse.ok(trademarkDetailService.findSuspciondetails(applicationNumber)) ;
    }

    /**
     * Retrieves a list of trademark info for the specified applicant code.
     *
     * @param code the applicant code to search for
     * @return a {@link ResponseEntity} containing a list of {@link TradeMarkInfo} objects
     */
    @GetMapping("applicant/{code}")
    public ResponseEntity<List<TradeMarkInfo>> getTradeMarkInfoByApplicantCode(@PathVariable String code) {
        return ResponseEntity.ok(trademarkDetailService.getTradeMarKApplicaionInfo(code));
    }

    @GetMapping("/registered-trademarks/applicant/{code}")
    public ResponseEntity<List<TradeMarkInfo>> getRegisteredTradeMarkInfoByApplicantCode(@PathVariable String code) {
        return ResponseEntity.ok(trademarkDetailService.getRegisteredTradeMarkInfoByApplicantCode(code));
    }

    /**
     * Retrieves trademark info for the specified applicant ID.
     *
     * @param id the ID of the applicant
     * @return a {@link ResponseEntity} containing a {@link TradeMarkInfo} object
     */
    @GetMapping("app/{id}")
    public ResponseEntity<TradeMarkInfo> getTradeMarkInfoByApplicantCode(@PathVariable Long id) {
        return ResponseEntity.ok(trademarkDetailService.getTradeMarkByApplicationId(id));
    }

    /**
     * Retrieves application trademark details for the specified application ID.
     *
     * @param applicationId the ID of the application
     * @return an {@link ApiResponse} containing the application trademark detail DTO
     */
    @GetMapping("/substantive-examination/{applicationId}")
    public ApiResponse<ApplicationTrademarkDetailDto> getApplicationSubstantiveExamination
            (@PathVariable(value = "applicationId") Long applicationId){
        ApplicationTrademarkDetailDto dto = trademarkDetailService.getApplicationTrademarkDetails(applicationId);
        return ApiResponse.ok(dto);
    }
    
    @GetMapping("/applications")
    List<ApplicationTrademarkDetailLightDto> findTrademarkApplicationDetailsByApplicationIds(@RequestParam("applicationIds")
                                                                                           List<Long> applicationIds){
        return ResponseEntity.ok(trademarkDetailService.getApplicationTrademarkDetails(applicationIds)).getBody();
    }

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

    /**
     * Download XML formatted file that contains an application information
     *
     * @param applicationId the ID of the application
     * @return an {@link ResponseEntity} containing the {@link ByteArrayResource} which is the XML file
     */
    @GetMapping("/application-info/{applicationId}/xml-file")
    public ResponseEntity<ByteArrayResource> getApplicationInfoXml(@PathVariable(value = "applicationId") Long applicationId) {
        ByteArrayResource file = trademarkDetailService.getApplicationInfoXml(applicationId);
        return DownloadFileUtils.builder()
                .file(file)
                .downloadedFileName("Trademarks.xml")
                .build();
    }
    @PostMapping("/applicant-trademarks-details")
    public ApiResponse<PaginationDto> getTrademarksByCustomerCode(
            @RequestBody RequestTrademarkDetailsDto requestTrademarkDetailsDto,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        log.info("Received requestTrademarkDetailsDto: {}", requestTrademarkDetailsDto);
        return ApiResponse.ok(applicationInfoService.getTrademarksByCustomerCode(requestTrademarkDetailsDto, page, limit));
    }
    
    @PutMapping("/edit-img")
    ApiResponse<Long> updateApplicationTrademarkDetailsImg(@RequestBody ApplicationInfoRequestLightDto applicationInfoRequestLightDto){
        return ApiResponse.ok(trademarkApplicationFacade.updateApplicationTrademarkDetailsImg(applicationInfoRequestLightDto));
    }
    
    @GetMapping("/basic-info/applications/{appId}")
    ApiResponse<TradeMarkLightDto> getTrademarkLightDetails(@PathVariable(name = "appId") Long appId){
        return ApiResponse.ok(trademarkDetailService.getTradeMarkLightDetails(appId));
    }

    @GetMapping("/{appId}/type/verbal")
    ApiResponse<Boolean> checkIfTrademarkTypeVerbal(@PathVariable(name = "appId") Long appId){
        return ApiResponse.ok(trademarkDetailService.isTrademarkTypeVerbal(appId));
    }


    @GetMapping("/summary/{appId}")
    @Operation(description = "**This for retrieve application details,applicants,publications**")
    ApiResponse<ApplicationTrademarkDetailSummaryDto> getTrademarkApplicationSummary(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(trademarkDetailService.getTradeMarkApplicationDetails(appId));
    }

    @GetMapping("/task-doc/{appId}")
    @Operation(description = "**This for retrieve application tasks or documents or both**")
    ApiResponse<TradeMarkThirdPartyDto> getThirdPartyIntegrationResults(@PathVariable(name = "appId") Long appId,
                                                                        @Parameter(in = ParameterIn.QUERY, description = "Third party route", required = true, schema = @Schema(allowableValues = {"DOCUMENTS", "TASKS", "BOTH"}))
                                                                        @RequestParam(value = "ThirdPartyRoute", required = true)
    String route) {
        return ApiResponse.ok(trademarkDetailService.getThirdPartyIntegrationResults(appId, route));
    }



}
