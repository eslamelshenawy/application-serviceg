package gov.saip.applicationservice.common.controllers.industrial;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignLookupResDto;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The IndustrialDesignLookupController class is responsible for handling HTTP requests related to industrial design lookup.
 * It defines an endpoint for retrieving industrial design lookup data.
 *
 * <p>This class uses Spring MVC annotations to map HTTP requests to appropriate controller methods. It also uses
 * dependency injection to access the necessary services for handling industrial design requests.</p>
 *
 * <p>Endpoints:</p>
 * <ul>
 *     <li>GET /kc/lookup - retrieve industrial design lookup data</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Create a new instance of the IndustrialDesignLookupController class
 * IndustrialDesignLookupController controller = new IndustrialDesignLookupController(industrialDesignLookupService);
 *
 * // Retrieve the industrial design lookup data
 * ApiResponse<Object> response = controller.getIndustrialDesignLookup();
 * }</pre>
 */
@RestController()
@RequestMapping("kc/industrial/lookup")
@RequiredArgsConstructor
public class IndustrialDesignLookupController {

    private final IndustrialDesignLookupService industrialDesignLookupService;

    /**
     * Retrieves industrial design lookup data.
     *
     * @return an ApiResponse containing the industrial design lookup data
     */
    @GetMapping
    public ApiResponse<IndustrialDesignLookupResDto> getIndustrialDesignLookup(){
        return ApiResponse.ok(industrialDesignLookupService.getIndustrialDesignLookup());
    }

}
