package gov.saip.applicationservice.common.controllers.trademark.lookup;


import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.trademark.LkTagLanguageDto;
import gov.saip.applicationservice.common.model.trademark.LkTagLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping({"kc/tag-language", "/internal-calling/tag-language"})
@RequiredArgsConstructor
public class LkTagLanguageController extends BaseLkpController<LkTagLanguage, LkTagLanguageDto, Integer> {

}
