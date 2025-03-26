package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationDrawingDto;
import gov.saip.applicationservice.common.mapper.ApplicationDrawingMapper;
import gov.saip.applicationservice.common.model.ApplicationDrawing;
import gov.saip.applicationservice.common.response.ApplicationDrawingResponse;
import gov.saip.applicationservice.common.service.ApplicationDrawingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController()
@RequestMapping(value = {"/kc/app-drawing", "/internal-calling/app-drawing"})
@RequiredArgsConstructor
public class ApplicationDrawingController extends BaseController<ApplicationDrawing, ApplicationDrawingDto, Long> {

    private final ApplicationDrawingService applicationDrawingService;
    private final ApplicationDrawingMapper applicationDrawingMapper;
    @Override
    protected BaseService<ApplicationDrawing, Long> getService() {
        return applicationDrawingService;
    }

    @Override
    protected BaseMapper<ApplicationDrawing, ApplicationDrawingDto> getMapper() {
        return applicationDrawingMapper;
    }
    @GetMapping("/shapes/{appId}")
    public ApiResponse<ApplicationDrawingResponse> getAppDrawing(@PathVariable(name = "appId") Long appId) {
        List<ApplicationDrawing> entities = applicationDrawingService.getAppDrawing(appId);
        List<ApplicationDrawingDto> dtos = applicationDrawingMapper.map(entities);
        return ApiResponse.ok(handleAppDrawingResponse(dtos));
    }

    @DeleteMapping("/shapes/{appId}/{documentId}")
    public ApiResponse<Void> deleteByAppAndDocumentId(@PathVariable Long appId, @PathVariable Long documentId) {
        applicationDrawingService.deleteByAppAndDocumentId(appId, documentId);
        return ApiResponse.noContent();
    }

    @PostMapping("/create-shapes")
    public ApiResponse<List<Long>> createApplicationDrawings(@Valid @RequestBody List<ApplicationDrawingDto> requestList) {
        List<Long> applicationDrawings = applicationDrawingService.createApplicationDrawings(requestList);
        return ApiResponse.ok(applicationDrawings);
    }
    @DeleteMapping("/shapes-list/{appId}")
    public ApiResponse<Void> deleteById(@PathVariable List<Long> appId) {
        applicationDrawingService.deleteById(appId);
        return ApiResponse.noContent();
    }

    ApplicationDrawingResponse handleAppDrawingResponse(List<ApplicationDrawingDto> dtos){
        ApplicationDrawingResponse response = new ApplicationDrawingResponse();
        List<ApplicationDrawingDto> notDefaultDrawings = dtos.stream().filter(i -> {
            if (i.isDefault()) {
                response.setMainDraw(i);
            }
            return !i.isDefault();
        }).toList();
        response.setDrawings(notDefaultDrawings);
        return response;
    }
}
