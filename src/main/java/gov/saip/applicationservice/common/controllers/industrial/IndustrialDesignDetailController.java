package gov.saip.applicationservice.common.controllers.industrial;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.industrial.ApplicationIndustrialDesignDetailDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignDetailDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignDetailReqDto;
import gov.saip.applicationservice.common.dto.industrial.PublicationDetailsDto;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.DocumentTypeEnum;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignDetailService;
import gov.saip.applicationservice.util.DownloadFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * The IndustrialDesignDetailController class is responsible for handling HTTP requests related to industrial design
 * details. It defines endpoints for creating, retrieving, and updating industrial design details, as well as for
 * retrieving application industrial design details and substantive examination details.
 *
 * <p>This class uses Spring MVC annotations to map HTTP requests to appropriate controller methods. It also uses
 * dependency injection to access the necessary services for handling industrial design requests.</p>
 *
 * <p>Endpoints:</p>
 * <ul>
 *     <li>POST /kc/industrial-Design-detail/application/{id} - create a new industrial design detail for an application</li>
 *     <li>GET /kc/industrial-Design-detail/{id} - retrieve an industrial design detail by ID</li>
 *     <li>GET /kc/industrial-Design-detail/application/{id} - retrieve an industrial design detail by application ID</li>
 *     <li>GET /kc/industrial-Design-detail/substantive-examination/{applicationId} - retrieve substantive examination details for an application</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Create a new instance of the IndustrialDesignDetailController class
 * IndustrialDesignDetailController controller = new IndustrialDesignDetailController(industrialDesignDetailService);
 *
 * // Create a new industrial design detail for an application
 * IndustrialDesignDetailReqDto createDto = new IndustrialDesignDetailReqDto();
 * createDto.setDesignName("My Design");
 * createDto.setOwnerName("John Doe");
 * ApiResponse<Object> response = controller.create(createDto, 1L);
 *
 * // Retrieve an industrial design detail by ID
 * ResponseEntity<IndustrialDesignDetailDto> response = controller.findDtoById(1L);
 *
 * // Retrieve an industrial design detail by application ID
 * ResponseEntity<IndustrialDesignDetailDto> response = controller.findDtoByApplicationId(1L);
 *
 * // Retrieve substantive examination details for an application
 * ApiResponse<ApplicationIndustrialDesignDetailDto> response = controller.getApplicationSubstantiveExamination(1L);
 * }</pre>
 */
@RestController()
@RequestMapping({"kc/industrial-Design-detail", "/internal-calling/industrial-Design-detail","/pb/industrial_design"})
@RequiredArgsConstructor
public class IndustrialDesignDetailController {

    private final IndustrialDesignDetailService industrialDesignDetailService;


    /**
     * Creates a new industrial design detail for an application.
     *
     * @param req the IndustrialDesignDetailReqDto object containing the details of the industrial design detail to create
     * @param id the ID of the application to associate the industrial design detail with
     * @return an ApiResponse containing the IndustrialDesignDetailDto object representing the created industrial design detail
     */
    @PostMapping("/application/{id}")
    public ApiResponse<Object> create(@Valid @RequestBody IndustrialDesignDetailReqDto req, @PathVariable Long id) {
        return ApiResponse.created(industrialDesignDetailService.create(req, id));
    }

    /**
     * Retrieves an industrial design detail by ID.
     *
     * @param id the ID of the industrial design detail to retrieve
     * @return a ResponseEntity containing the IndustrialDesignDetailDto object representing the retrieved industrial design detail
     */
    @GetMapping("/{id}")
    public ResponseEntity<IndustrialDesignDetailDto> findDtoById(@PathVariable Long id) {
        IndustrialDesignDetailDto dto = industrialDesignDetailService.findDtoById(id);
        return ResponseEntity.ok(dto);

    }

    /**
     * Retrieves substantive examination details for an application.
     *
     * @param applicationId the ID of the application to retrieve substantive examination details for
     * @return an ApiResponse containing the ApplicationIndustrialDesignDetailDto object representing the retrieved substantive examination details
     */
    @GetMapping("/substantive-examination/{applicationId}")
    public ApiResponse<ApplicationIndustrialDesignDetailDto> getApplicationSubstantiveExamination(@PathVariable Long applicationId) {
        return ApiResponse.ok(industrialDesignDetailService.getApplicationIndustrialDesignDetails(applicationId));
    }

    /**
     * Retrieves Publication details for an application.
     *
     * @param applicationId the ID of the application to retrieve publication details for
     * @return an ApiResponse containing the PublicationDetailsDto object representing the retrieved publication details
     */
    @GetMapping("/publication/{applicationId}")
    public ApiResponse<PublicationDetailsDto> getPublicationDetails(@PathVariable Long applicationId,
                                                                    @RequestParam(value = "applicationPublicationId", required = false) Long applicationPublicationId) {
        return ApiResponse.ok(industrialDesignDetailService.getPublicationDetails(applicationId, applicationPublicationId));
    }

    /**
     * Retrieves an industrial design detail by application ID.
     *
     * @param id the ID of the application associated with the industrial design detail to retrieve
     * @return an ApiResponse containing the IndustrialDesignDetailDto object representing the retrieved industrial design detail
     */
    @GetMapping("/application/{id}")
    public ApiResponse<IndustrialDesignDetailDto> findDtoByApplicationId(@PathVariable Long id) {
        return ApiResponse.ok(industrialDesignDetailService.findDtoByApplicationId(id));

    }


    /**
     * Download XML formatted file that contains the industrial design application information
     *
     * @param applicationId the ID of the application
     * @return an {@link ResponseEntity} containing the {@link ByteArrayResource} which is the XML file
     */
    @GetMapping("/application-info/{applicationId}/xml-file")
    public ResponseEntity<ByteArrayResource> getApplicationInfoXml(@PathVariable(value = "applicationId") Long applicationId) {
        ByteArrayResource file = industrialDesignDetailService.getApplicationInfoXml(applicationId);
        return DownloadFileUtils.builder()
                .file(file)
                .downloadedFileName("IndustrialDesigns.xml")
                .build();
    }

    @PostMapping("/pdf/{reportName}")
    ApiResponse<List<DocumentDto>> generateJasperPdf(@RequestBody ReportRequestDto dto , @PathVariable(name = "reportName") String reportName
            , @RequestParam(value = "documentType") DocumentTypeEnum documentType) throws IOException {
        List<DocumentDto>  documentDtos= industrialDesignDetailService.generateJasperPdf(dto,reportName , documentType);
        return ApiResponse.ok(documentDtos);
    }


}
