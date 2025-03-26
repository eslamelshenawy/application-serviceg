package gov.saip.applicationservice.common.controllers.industrial;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignLookupResDto;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The InternalCallingController class is responsible for handling HTTP requests related to internal calling.
 * It defines endpoints for creating, retrieving, and updating industrial design details, as well as for retrieving
 * industrial design lookup data.
 *
 * <p>This class uses Spring MVC annotations to map HTTP requests to appropriate controller methods. It also uses
 * dependency injection to access the necessary services for handling industrial design requests.</p>
 *
 * <p>Endpoints:</p>
 * <ul>
 *     <li>POST /internal-calling/industrial-Design-detail/application/{id} - create a new industrial design detail for an application</li>
 *     <li>GET /internal-calling/industrial-Design-detail/application/{id} - retrieve an industrial design detail by application ID</li>
 *     <li>GET /internal-calling/lookup - retrieve industrial design lookup data</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Create a new instance of the InternalCallingController class
 * InternalCallingController controller = new InternalCallingController(industrialDesignDetailService, industrialDesignLookupService);
 *
 * // Create a new industrial design detail for an application
 * IndustrialDesignDetailReqDto createDto = new IndustrialDesignDetailReqDto();
 * createDto.setDesignName("My Design");
 * createDto.setOwnerName("John Doe");
 * ApiResponse<Object> response = controller.create(createDto, 1L);
 *
 * // Retrieve an industrial design detail by application ID
 * ApiResponse<IndustrialDesignDetailDto> response = controller.findDtoByApplicationId(1L);
 *
 * // Retrieve the industrial design lookup data
 * ApiResponse<Object> response = controller.getIndustrialDesignLookup();
 * }</pre>
 */
@RestController()
@RequestMapping("/internal-calling/lookup")
@RequiredArgsConstructor
public class LookupController {

    private final IndustrialDesignLookupService industrialDesignLookupService;


    /**
     * Retrieves industrial design lookup data.
     *
     * @return an ApiResponse containing the industrial design lookup data
     */
    @GetMapping("")
    public ApiResponse<IndustrialDesignLookupResDto> getIndustrialDesignLookup(){
        return ApiResponse.ok(industrialDesignLookupService.getIndustrialDesignLookup());
    }


}
