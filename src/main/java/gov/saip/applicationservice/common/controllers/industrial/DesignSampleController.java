package gov.saip.applicationservice.common.controllers.industrial;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.industrial.CreateDesignSampleDto;
import gov.saip.applicationservice.common.dto.industrial.DesignSampleReqDto;
import gov.saip.applicationservice.common.dto.industrial.DesignSampleResDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignDetailDto;
import gov.saip.applicationservice.common.service.industrial.DesignSampleDrawingsService;
import gov.saip.applicationservice.common.service.industrial.DesignSampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The DesignSampleController class is responsible for handling HTTP requests related to design samples. It defines
 * an endpoint for creating design samples, which takes a CreateDesignSampleDto object as input and returns an
 * IndustrialDesignDetailDto object as output.
 *
 * <p>This class uses Spring MVC annotations to map HTTP requests to appropriate controller methods. It also uses
 * dependency injection to access the DesignSampleService for handling design sample requests.</p>
 *
 * <p>Endpoints:</p>
 * <ul>
 *     <li>POST /kc/design-samples - create a new design sample</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Create a new instance of the DesignSampleController class
 * DesignSampleController controller = new DesignSampleController(designSampleService);
 *
 * // Create a new design sample
 * CreateDesignSampleDto createDto = new CreateDesignSampleDto();
 * createDto.setIndustrialDesignId(1L);
 * ResponseEntity<IndustrialDesignDetailDto> response = controller.createDesignSamples(createDto);
 * }</pre>
 */
@RestController()
@RequestMapping({"kc/design-samples","/internal-calling/design-samples"})
@RequiredArgsConstructor
public class DesignSampleController {

    private final DesignSampleService designSampleService;
    private final DesignSampleDrawingsService designSampleDrawingsService;

    /**
     * Creates a new design sample.
     *
     * @param designSampleReqDtos the CreateDesignSampleDto object containing the details of the design sample to create
     * @return a ResponseEntity containing the IndustrialDesignDetailDto object representing the created design sample
     */
    @PostMapping
    public ResponseEntity<IndustrialDesignDetailDto> createDesignSamples(@RequestBody CreateDesignSampleDto designSampleReqDtos) {
        return ResponseEntity.ok(designSampleService.create(designSampleReqDtos));
    }
    @PutMapping
    public ResponseEntity<IndustrialDesignDetailDto> updateDesignSamples(@RequestBody CreateDesignSampleDto designSampleReqDtos) {
        return ResponseEntity.ok(designSampleService.create(designSampleReqDtos));
    }
    /**
     * Count design samples.
     *
     * @param designId the id of which designs belong to
     * @return a number of design samples
     */
    @GetMapping("/{designId}")
    public ResponseEntity<Long> countDesignSamples(@PathVariable(name = "designId") String designId) {
        return ResponseEntity.ok(designSampleService.count(Long.valueOf(designId)));
    }

    @PutMapping("/update-designers")
    public ResponseEntity<DesignSampleReqDto> updateDesigners(@RequestBody DesignSampleReqDto designSampleReqDto) {
        return ResponseEntity.ok(designSampleService.updateDesigners(designSampleReqDto));
    }
    @PutMapping("/update-classification")
    public ApiResponse<String> updateClassification(@RequestBody DesignSampleReqDto designSampleReqDto) {
        designSampleService.updateClassifications(designSampleReqDto);
        return ApiResponse.ok("successfully apply changes");
    }
    @PutMapping("/update-name")
    public ResponseEntity<DesignSampleReqDto> updateName(@RequestBody DesignSampleReqDto designSampleReqDto) {
        return ResponseEntity.ok(designSampleService.updateName(designSampleReqDto));
    }
    @PutMapping("/update-drawings")
    public ResponseEntity<IndustrialDesignDetailDto> updateDrawings(@RequestBody DesignSampleReqDto designSampleReqDtos) {
        return null;
    }

    @DeleteMapping("/{id}/delete-sample")
    public ApiResponse<String> deleteSample(@PathVariable Long id) {
        designSampleService.deleteSample(id);
        return ApiResponse.ok("Deleted");
    }
    @DeleteMapping("/{id}/delete-drawings")
    public ApiResponse<String> deleteDrawings(@PathVariable Long id) {
        designSampleDrawingsService.deleteDrawing(id);
        return ApiResponse.ok("Deleted");
    }

    @GetMapping("/sample-paginator")
    public ApiResponse<PaginationDto<List<DesignSampleResDto>>> findDtoByApplicationIdPaginator(
            @RequestParam(value = "appId",                 required     = true)    Long appId,
            @RequestParam(value = "page",                  defaultValue = "1")     Integer page,
            @RequestParam(value = "limit",                 defaultValue = "10")    Integer limit,
            @RequestParam(value = "query",                 required = false)       String query,
            @RequestParam(value = "withDescription",       required = false)       Boolean withDescription) {
        return ApiResponse.ok(designSampleService.findDesignSamplesByIndustrialDesignId(appId ,query,withDescription, page , limit));
    }
    @PutMapping("/update-description")
    public ApiResponse<String> updateDescription(@RequestBody DesignSampleReqDto designSampleReqDto) {
        designSampleService.updateDescription(designSampleReqDto);
        return ApiResponse.ok("successfully apply changes");
    }

}
