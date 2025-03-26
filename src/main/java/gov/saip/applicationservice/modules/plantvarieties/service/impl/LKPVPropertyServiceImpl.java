package gov.saip.applicationservice.modules.plantvarieties.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.plantvarieties.dto.LKPVPropertyDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.LKPVPropertyOptionsLightDto;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVPropertyType;
import gov.saip.applicationservice.modules.plantvarieties.mapper.LKPVPropertyMapper;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVProperty;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVPropertyOptions;
import gov.saip.applicationservice.modules.plantvarieties.model.LkVegetarianTypes;
import gov.saip.applicationservice.modules.plantvarieties.repository.LKPVPropertyRepository;
import gov.saip.applicationservice.modules.plantvarieties.service.LKPVPropertyOptionsService;
import gov.saip.applicationservice.modules.plantvarieties.service.LKPVPropertyService;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LKPVPropertyServiceImpl extends BaseServiceImpl<LKPVProperty,Long> implements LKPVPropertyService {

    private final LKPVPropertyRepository LKPVPropertyRepository;
    private final LKPVPropertyMapper LKPVPropertyMapper;
    private final LKPVPropertyOptionsService lkpvPropertyOptionsService;
    @Override
    protected BaseRepository<LKPVProperty, Long> getRepository() {
        return LKPVPropertyRepository;
    }


    @Override
    public LKPVProperty update(LKPVProperty entity) {
        Optional<LKPVProperty> entityOpt  = LKPVPropertyRepository.findById(entity.getId());
        if (entityOpt.isEmpty())
            throw new BusinessException(Constants.ErrorKeys.APP_ID_DOESNT_EXIST);
        return super.update(entity);
    }

    @Override
    public void softDeleteById(Long id) {
        List<LKPVPropertyOptions> lkpvPropertyOptions = lkpvPropertyOptionsService.findByLKPVPropertyId(id);
        lkpvPropertyOptions.stream().forEach(val -> {
            val.setIsDeleted(1);
        });
       LKPVPropertyRepository.updateIsDeleted(id , 1);
    }

    @Override
    public PaginationDto getAllPaginatedPvProperties(int page, int limit, String search, Long lkVegetarianTypeId, PVPropertyType type, PVExcellence excellence,Boolean isActive,String language) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<LKPVProperty> lkPvProperties = LKPVPropertyRepository.getAllPaginatedPvProperties(search,lkVegetarianTypeId,type,excellence,isActive,language,pageable);
        List<LKPVPropertyDto> lkpvPropertyDtos = LKPVPropertyMapper.map(lkPvProperties.getContent());
        return PaginationDto.builder()
                .content(lkpvPropertyDtos)
                .totalPages(lkPvProperties.getTotalPages())
                .totalElements(lkPvProperties.getTotalElements())
                .build();
    }

    @Override
    public PaginationDto getAllPaginatedPvPropertiesThatHaveOptionsOnly(int page, int limit,Long lkVegetarianTypeId, PVPropertyType type) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<LKPVProperty> lkPvProperties = LKPVPropertyRepository.getAllPaginatedPvPropertiesThatHaveOptionsOnly(lkVegetarianTypeId,type,pageable);
        List<LKPVPropertyDto> lkpvPropertyDtos = LKPVPropertyMapper.map(lkPvProperties.getContent());
        lkpvPropertyDtos.stream().forEach(val -> {
            List<LKPVPropertyOptionsLightDto> lkpvPropertyOptionsLightDtoList = lkpvPropertyOptionsService.getAllPvPropertiesOptionsLightDto(val.getId());
            if (Objects.nonNull(lkpvPropertyOptionsLightDtoList)){
                val.setLkpvPropertyOptionsLightDtoList(lkpvPropertyOptionsLightDtoList);
            }
        });
        return PaginationDto.builder()
                .content(lkpvPropertyDtos)
                .totalPages(lkPvProperties.getTotalPages())
                .totalElements(lkPvProperties.getTotalElements())
                .build();
    }

    @Override
    public List<LKPVPropertyDto> getAllPvPropertiesWithoutPaging(Long lkVegetarianTypeId, PVPropertyType type, PVExcellence excellence) {
        List<LKPVPropertyDto> lkpvPropertyDtos = LKPVPropertyMapper.map(LKPVPropertyRepository.getAllPvPropertiesWithoutPaging(lkVegetarianTypeId,type,excellence));
        return lkpvPropertyDtos;
    }

    @Override
    public List<LKPVPropertyDto> getAllPvPropertiesThatHaveOptionsOnlyWithoutPaging(Long lkVegetarianTypeId,PVExcellence excellence, PVPropertyType type) {
        List<LKPVPropertyDto> lkpvPropertyDtos = LKPVPropertyMapper.map(LKPVPropertyRepository.getAllPvPropertiesThatHaveOptionsOnlyWithoutPaging(lkVegetarianTypeId,excellence,type));
        lkpvPropertyDtos.stream().forEach(val -> {
            List<LKPVPropertyOptionsLightDto> lkpvPropertyOptionsLightDtoList = lkpvPropertyOptionsService.getAllPvPropertiesOptionsLightDto(val.getId());
            if (Objects.nonNull(lkpvPropertyOptionsLightDtoList)){
                val.setLkpvPropertyOptionsLightDtoList(lkpvPropertyOptionsLightDtoList);
            }
        });
        return lkpvPropertyDtos;
    }
    @Transactional
    @Override
    public String processExcelFile(MultipartFile file, Long lkVegetarianTypeId, PVExcellence excellence, PVPropertyType type) {
        if (file.isEmpty()) {
            return "Please upload a valid XLSX file.";
        }
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            // Validate the number of columns
            Row headerRow = sheet.getRow(0);
            if (headerRow == null || headerRow.getPhysicalNumberOfCells() != 4) {
                throw new BusinessException("The Excel file must have exactly 4 columns.", HttpStatus.BAD_REQUEST, null);
            }
            List<LKPVProperty> dataList = new ArrayList<>();
            int rowCount = 0;
            for (Row row : sheet) {
                rowCount++;
                if (row.getRowNum() == 0) { // Skip header row
                    continue;
                }
                // Check if all required cells are present in the row
                for (int i = 0; i < 4; i++) {
                    if (i != 2 && (row.getCell(i) == null || Utilities.getCellValue(row.getCell(i)).isEmpty())) {
                        throw new BusinessException("Row " + rowCount + " has missing or invalid data.", HttpStatus.BAD_REQUEST, null);
                    }
                }
                LKPVProperty data = new LKPVProperty();
                data.setNameAr(Utilities.getCellValue(row.getCell(0)));
                data.setNameEn(Utilities.getCellValue(row.getCell(1)));
                data.setCode(Utilities.getCellValue(row.getCell(2)));
                data.setIsActive(Boolean.parseBoolean(Utilities.getCellValue(row.getCell(3))));
                data.setLkVegetarianType(new LkVegetarianTypes(lkVegetarianTypeId));
                data.setExcellence(PVExcellence.YES);
                data.setType(type);
                dataList.add(data);
            }
            LKPVPropertyRepository.saveAll(dataList);
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
