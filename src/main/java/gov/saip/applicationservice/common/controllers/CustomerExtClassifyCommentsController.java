package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.CustomerExtClassifyCommentsDto;
import gov.saip.applicationservice.common.service.impl.CustomerExtClassifyCommentsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping({"/kc/v1/customer-ext-classify-comments", "/internal-calling/v1/customer-ext-classify-comments"})
public class CustomerExtClassifyCommentsController {

    private final CustomerExtClassifyCommentsServiceImpl customerExtClassifyCommentsService;

    @GetMapping()
    public ApiResponse<?> getAll() {
        return ApiResponse.ok(customerExtClassifyCommentsService.findAll());
    }

    @PostMapping()
    public ApiResponse<?> createCustomerExtClassifyComment(@RequestBody CustomerExtClassifyCommentsDto customerExtClassifyCommentsDto) {
        return ApiResponse.ok(customerExtClassifyCommentsService.createCustomerExtClassifyComment(customerExtClassifyCommentsDto));
    }

    @PutMapping()
    public ApiResponse<?> updateCustomerExtClassifyComment(@RequestBody CustomerExtClassifyCommentsDto customerExtClassifyCommentsDto) {
        return ApiResponse.ok(customerExtClassifyCommentsService.updateCustomerExtClassifyComment(customerExtClassifyCommentsDto));
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<?> deleteCustomerExtClassifyComment(@PathVariable Long commentId) {
        customerExtClassifyCommentsService.deleteCustomerExtClassifyComment(commentId);
        return ApiResponse.ok("Success");
    }
}
