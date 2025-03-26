package gov.saip.applicationservice.modules.plantvarieties.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.plantvarieties.dto.LKPVPropertyOptionsLightDto;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVPropertyType;
import gov.saip.applicationservice.modules.plantvarieties.mapper.LKPVPropertyOptionsMapper;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVProperty;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVPropertyOptions;
import gov.saip.applicationservice.modules.plantvarieties.model.LkVegetarianTypes;
import gov.saip.applicationservice.modules.plantvarieties.repository.LKPVPropertyOptionsRepository;
import gov.saip.applicationservice.modules.plantvarieties.service.LKPVPropertyOptionsService;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LKPVPropertyOptionsServiceImpl extends BaseServiceImpl<LKPVPropertyOptions,Long>
implements LKPVPropertyOptionsService {

    private final LKPVPropertyOptionsRepository lkpvPropertyOptionsRepository;
    private final LKPVPropertyOptionsMapper lkpvPropertyOptionsMapper;

    @Override
    protected BaseRepository<LKPVPropertyOptions, Long> getRepository() {
        return lkpvPropertyOptionsRepository;
    }

    @Override
    public void softDeleteById(Long id) {
        lkpvPropertyOptionsRepository.updateIsDeleted(id,1);
    }

    @Override
    public PaginationDto getAllPaginatedPVPropertyOptions(int page, int limit, Long lkPVPropertyId, PVPropertyType type, PVExcellence excellence,Long lkVegetarianTypeId,Boolean isActive,String language, String search) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<LKPVPropertyOptions> allPaginatedPvPropertyOptions = lkpvPropertyOptionsRepository.getAllPaginatedPvPropertyOptions(pageable,lkPVPropertyId,type,excellence,lkVegetarianTypeId,isActive,language,search);
        return PaginationDto.builder()
                .content(lkpvPropertyOptionsMapper.map(allPaginatedPvPropertyOptions.getContent()))
                .totalPages(allPaginatedPvPropertyOptions.getTotalPages())
                .totalElements(allPaginatedPvPropertyOptions.getTotalElements())
                .build();
    }

    @Override
    public List<LKPVPropertyOptions> getAllPVPropertyOptions(Long lkPVPropertyId, PVPropertyType type, PVExcellence excellence, String search) {
        return lkpvPropertyOptionsRepository.getAllPvPropertyOptions(lkPVPropertyId,type,excellence,search);
    }

    @Override
    public List<LKPVPropertyOptions> findByLKPVPropertyId(Long id) {
        return lkpvPropertyOptionsRepository.findByLKPVPropertyId(id);
    }

    @Override
    public List<LKPVPropertyOptionsLightDto> getAllPvPropertiesOptionsLightDto(Long lkPVPropertyId) {
        return lkpvPropertyOptionsRepository.getAllPvPropertiesOptionsLightDto(lkPVPropertyId);
    }
    @Transactional
    @Override
    public String processExcelFile(MultipartFile file, Long lkPVPropertyId) {
        if (file.isEmpty()) {
            return "Please upload a valid XLSX file.";
        }
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            // Validate the number of columns
            Row headerRow = sheet.getRow(0);
            if (headerRow == null || headerRow.getPhysicalNumberOfCells() != 6) {
                throw new BusinessException("The Excel file must have exactly 6 columns.", HttpStatus.BAD_REQUEST, null);
            }
            List<LKPVPropertyOptions> dataList = new ArrayList<>();
            int rowCount = 0;

            for (Row row : sheet) {
                rowCount++;
                if (row.getRowNum() == 0) { // Skip header row
                    continue;
                }

                // Check if all required cells are present in the row
                for (int i = 0; i < 6; i++) {
                    if (i != 2 && i != 3 && i != 4 && (row.getCell(i) == null || Utilities.getCellValue(row.getCell(i)).isEmpty())) {
                        throw new BusinessException("Row " + rowCount + " has missing or invalid data.", HttpStatus.BAD_REQUEST, null);
                    }
                }
                LKPVPropertyOptions data = new LKPVPropertyOptions();
                data.setNameAr(Utilities.getCellValue(row.getCell(0)));
                data.setNameEn(Utilities.getCellValue(row.getCell(1)));
                data.setCode(Utilities.getCellValue(row.getCell(2)));
                data.setExample(Utilities.getCellValue(row.getCell(3)));
                data.setNote(Utilities.getCellValue(row.getCell(4)));
                data.setIsActive(Boolean.parseBoolean(Utilities.getCellValue(row.getCell(5))));
                data.setLKPVProperty(new LKPVProperty(lkPVPropertyId));
                dataList.add(data);
            }
            lkpvPropertyOptionsRepository.saveAll(dataList);
            return "File uploaded and processed successfully.";
        } catch (BusinessException e) {
            return e.getMessage();
        } catch (IOException e) {
            return "Failed to process the file.";
        } catch (Exception e) {
            return "Unexpected error occurred: " + e.getMessage();
        }
    }
}
