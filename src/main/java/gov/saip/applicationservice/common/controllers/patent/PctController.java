package gov.saip.applicationservice.common.controllers.patent;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PctIValidateDto;
import gov.saip.applicationservice.common.dto.patent.PctRequestDto;
import gov.saip.applicationservice.common.model.patent.Pct;
import gov.saip.applicationservice.common.service.patent.PctService;
import gov.saip.applicationservice.common.validators.PctValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The {@code PctController} class provides RESTful web services for managing PCT applications.
 */
@RestController
@RequestMapping({"/kc/pct"})
@RequiredArgsConstructor
public class PctController {

    private final PctService pctService;

    private final PctValidator pctValidator;

    /**
     * Create a new PCT application.
     *
     * @param pctRequestDto the PCT request DTO
     * @return an {@link ApiResponse} containing the created PCT application
     */

    @PostMapping
    public ApiResponse<Object> createPct(@Valid @RequestBody PctRequestDto pctRequestDto) {
        return ApiResponse.created(pctService.createPct(pctRequestDto));
    }

    /**
     * Get a PCT application by ID.
     *
     * @param id the PCT ID
     * @return an {@link ApiResponse} containing the PCT application
     */
    @GetMapping("/{id}")
    public ApiResponse<Object> getPctById(@PathVariable Long id) {
        return ApiResponse.ok(pctService.getPctById(id));
    }

    /**
     * Get a PCT application by application ID.
     *
     * @param id the application ID
     * @return an {@link ApiResponse} containing the PCT application
     */
    @GetMapping("/application/{id}")
    public ApiResponse<Pct> getByApplicationId(@PathVariable Long id) {
        return ApiResponse.ok(pctService.findByApplicationId(id));
    }


    @GetMapping("/petitionNumber/{number}")
    public ApiResponse<PctIValidateDto> validatePetitionNumber(@PathVariable String number) {
        return ApiResponse.ok(pctService.validatePetitionNumberAndGetPctNumber(number));
    }


}
