package gov.saip.applicationservice.common.controllers.trademark;


import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.trademark.TrademarkLookupResDto;
import gov.saip.applicationservice.common.service.trademark.TrademarkLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing trademark lookup data.
 *
 * This class provides an endpoint for retrieving trademark lookup data.
 * The trademark lookup data is represented as a JSON object.
 *
 * <p>The controller class uses a {@link TrademarkLookupService} to retrieve the data.
 * The service is injected into the class using the {@link RequiredArgsConstructor} annotation.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * TrademarkLookupController trademarkLookupController = new TrademarkLookupController();
 * ApiResponse<Object> response = trademarkLookupController.getTrademarkLookup();
 * }</pre>
 *
 * @see TrademarkLookupService
 */
@RestController()
@RequestMapping({"kc/trademark/lookup", "internal-calling/trademark/lookup"})
@RequiredArgsConstructor
public class TrademarkLookupController {

    private final TrademarkLookupService trademarkLookupService;

    /**
     * Retrieves trademark lookup data.
     *
     * @return an {@link ApiResponse} containing the trademark lookup data
     */
    @GetMapping
public ApiResponse<TrademarkLookupResDto> getTrademarkLookup(@RequestParam(value ="applicationId", required = false)Long applicationId){
        return ApiResponse.ok(trademarkLookupService.getTrademarkLookup(applicationId));
    }




}
