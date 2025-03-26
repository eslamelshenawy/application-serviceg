package gov.saip.applicationservice.common.controllers.lookup;


import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.LkAttributeDto;
import gov.saip.applicationservice.common.dto.LkNoteCategoryDto;
import gov.saip.applicationservice.common.model.LkAttribute;
import gov.saip.applicationservice.common.model.LkNoteCategory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"kc/note-category" , "internal-calling/note-category"})
public class LKNoteCategoryController extends BaseLkpController <LkNoteCategory, LkNoteCategoryDto, Integer> {
}
