package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.common.dto.LkClassificationVersionDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.model.LkClassificationVersion;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LkClassificationVersionService extends BaseLkService<LkClassificationVersion, Integer> {

    List<LkClassificationVersionDto> getClassificationVersions();

    Integer getLatestVersionIdBySaipCode(String saipCode);

    Integer getLatestVersionIdByCategoryId(Long categoryId);

    PaginationDto findAllClassificationVersionsBySearch(Integer page, Integer limit, String sortableColumn, String search,Long categoryId);

    List<LkClassificationVersionDto> findAllClassificationVersionsByCategory(Long categoryId);

    LkClassificationVersionDto findLatestClassificationVersionsWithClassificationByCategory(Long categoryId);

    void reedLocarnoSheet(MultipartFile file, Long categoryId);

    Integer saveLocarno(LkClassificationVersionDto lkClassificationVersionDto);

    void deleteLocarno(Integer id);
}
