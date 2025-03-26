package gov.saip.applicationservice.modules.plantvarieties.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusDto;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.plantvarieties.dto.LkVegetarianTypeDto;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.mapper.LkVegetarianTypeMapper;
import gov.saip.applicationservice.modules.plantvarieties.model.LkVegetarianTypes;
import gov.saip.applicationservice.modules.plantvarieties.repository.LkVegetarianTypeRepository;
import gov.saip.applicationservice.modules.plantvarieties.service.LKPVPropertyOptionsService;
import gov.saip.applicationservice.modules.plantvarieties.service.LkVegetarianTypeService;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LkVegetarianTypeServiceImpl extends BaseServiceImpl<LkVegetarianTypes, Long> implements LkVegetarianTypeService {

    private final LkVegetarianTypeRepository lkVegetarianTypeRepository;
    private final LKPVPropertyOptionsService lkpvPropertyOptionsService;
    private final LkVegetarianTypeMapper lkVegetarianTypeMapper;
    @Override
    protected BaseRepository<LkVegetarianTypes, Long> getRepository() {
        return  lkVegetarianTypeRepository;
    }

    @Override
    public LkVegetarianTypes update(LkVegetarianTypes entity) {
       Optional<LkVegetarianTypes> entityOpt = lkVegetarianTypeRepository.findById(entity.getId());
        if (entityOpt.isEmpty())
            throw new BusinessException(Constants.ErrorKeys.APP_ID_DOESNT_EXIST);
        return super.update(entity);
    }

    @Override
    public void softDeleteById(Long id){
        lkVegetarianTypeRepository.updateIsDeleted(id , 1);
    }

    @Override
    public PaginationDto getAllPaginatedVegetarianTypes(String search,Boolean isActive,int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<LkVegetarianTypes> lkVegetarianTypes = lkVegetarianTypeRepository.getAllPaginatedVegetarianTypes(search,isActive,pageable);
        return PaginationDto.builder()
                .content(lkVegetarianTypes.getContent())
                .totalPages(lkVegetarianTypes.getTotalPages())
                .totalElements(lkVegetarianTypes.getTotalElements())
                .build();
    }


    @Override
    public List<LkVegetarianTypes> findAll() {
        return lkVegetarianTypeRepository.getAllWithNoPaging();
    }


    @Override
    public List<LkVegetarianTypes> getAllVegetarianTypesThatHaveOnlyPropertiesAndOptions(PVExcellence excellence) {
        return lkVegetarianTypeRepository.getAllVegetarianTypesThatHaveOnlyPropertiesAndOptions(excellence);
    }

    @Override
    public String processExcelFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "Please upload a valid XLSX file.";
        }
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            // Validate the number of columns
            Row headerRow = sheet.getRow(0);
            if (headerRow == null || headerRow.getPhysicalNumberOfCells() != 8) {
                throw new BusinessException("The Excel file must have exactly 8 columns.", HttpStatus.BAD_REQUEST, null);
            }
            List<LkVegetarianTypes> dataList = new ArrayList<>();
            int rowCount = 0;
            for (Row row : sheet) {
                rowCount++;
                if (row.getRowNum() == 0) { // Skip header row
                    continue;
                }
                // Check if all required cells are present in the row
                for (int i = 0; i < 8; i++) {
                    if (i != 6 && (row.getCell(i) == null || Utilities.getCellValue(row.getCell(i)).isEmpty())) {
                        throw new BusinessException("Row " + rowCount + " has missing or invalid data.", HttpStatus.BAD_REQUEST, null);
                    }
                }
                LkVegetarianTypes data = new LkVegetarianTypes();
                data.setNameAr(Utilities.getCellValue(row.getCell(0)));
                data.setNameEn(Utilities.getCellValue(row.getCell(1)));
                data.setScientificName(Utilities.getCellValue(row.getCell(2)));
                data.setProtectionPeriod(Long.parseLong(Utilities.getCellValue(row.getCell(3))));
                data.setMarketingPeriodInKsa(Long.parseLong(Utilities.getCellValue(row.getCell(4))));
                data.setMarketingPeriodOutKsa(Long.parseLong(Utilities.getCellValue(row.getCell(5))));
                data.setCode(Utilities.getCellValue(row.getCell(6)));
                data.setIsActive(Boolean.parseBoolean(Utilities.getCellValue(row.getCell(7))));
                dataList.add(data);
            }
            lkVegetarianTypeRepository.saveAll(dataList);
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
