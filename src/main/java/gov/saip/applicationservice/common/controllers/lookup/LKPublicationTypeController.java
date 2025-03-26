package gov.saip.applicationservice.common.controllers.lookup;


import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.lookup.LKPublicationTypeDto;
import gov.saip.applicationservice.common.dto.lookup.LkAcceleratedTypeDto;
import gov.saip.applicationservice.common.mapper.lookup.LKPublicationTypeMapper;
import gov.saip.applicationservice.common.model.LKPublicationType;
import gov.saip.applicationservice.common.service.lookup.LKPublicationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value={"/pb/publication-type" , "/internal-calling/publication-type"})
public class LKPublicationTypeController  extends BaseLkpController<LKPublicationType, LKPublicationTypeDto, Integer> {

    @Autowired
    private LKPublicationTypeService lkPublicationTypeService;
    @Autowired
    private LKPublicationTypeMapper publicationTypeMapper;

    @GetMapping("/category/{id}")
    public ApiResponse<List<LKPublicationTypeDto>> findByApplicationCategoryId(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok( publicationTypeMapper.map(lkPublicationTypeService.getPublicationTypes(id)));
    }
}
