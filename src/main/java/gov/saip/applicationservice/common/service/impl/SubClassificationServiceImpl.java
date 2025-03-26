package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.ListSubClassificationDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.SubClassificationDto;
import gov.saip.applicationservice.common.dto.Suspcion.SubClassificationDetailsProjection;
import gov.saip.applicationservice.common.mapper.SubClassificationMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.common.repository.SubClassificationRepository;
import gov.saip.applicationservice.common.service.ApplicationSubClassificationService;
import gov.saip.applicationservice.common.service.ClassificationService;
import gov.saip.applicationservice.common.service.RevokeProductsService;
import gov.saip.applicationservice.common.service.SubClassificationService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SubClassificationServiceImpl extends BaseServiceImpl<SubClassification, Long> implements SubClassificationService {

    private final SubClassificationRepository subClassRepository;
    private final SubClassificationMapper subClassMapper;
    private final ClassificationService classificationService;
    private final ApplicationRelevantTypeRepository applicationRelevantTypeRepository;

    @Override
    protected BaseRepository<SubClassification, Long> getRepository() {
        return subClassRepository;
    }


    private ApplicationSubClassificationService applicationSubClassificationService;
    private final RevokeProductsService revokeProductsService;

    @Override
    public PaginationDto getAllSubClass(int page, int limit, String query, Boolean isShortcut
            , long applicationId, long categoryId) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id"));
        if (!isShortcut){
            Page<SubClassification> subClass = subClassRepository.findBySearch(query,isShortcut, categoryId
                    ,  null, pageable);
            long count = applicationSubClassificationService.countByApplicationInfoId(applicationId);
            return PaginationDto.builder()
                    .content(mapSubClass(subClass.getContent(), applicationId, count, isShortcut))
                    .totalPages(subClass.getTotalPages())
                    .totalElements(subClass.getTotalElements())
                    .build();
        } else {
            Page<SubClassification> subClass = subClassRepository.findBySearch(query,isShortcut, categoryId
                    , null, pageable);
            long count = applicationSubClassificationService.countByApplicationInfoId(applicationId);
            return PaginationDto.builder()
                    .content(mapSubClass(subClass.getContent(), applicationId, count, isShortcut))
                    .totalPages(subClass.getTotalPages())
                    .totalElements(subClass.getTotalElements())
                    .build();
        }
    }

    @Override
    public PaginationDto findSubClassificationByTrademarkId(int page, int limit, String tmoCustomerCode, Long trademarkId, Long subClassificationId, String code) {
        if(!isTmoHasThisTrademark(tmoCustomerCode, trademarkId)){
            return getEmptyPage();
        }
        PageRequest pageable = PageRequest.of(page, limit);
        Page<SubClassificationDetailsProjection> subClassificationDetailsProjections = subClassRepository.findSubClassificationByTrademarkId(trademarkId, subClassificationId, code ,  pageable);

        return PaginationDto.builder()
                .content(subClassificationDetailsProjections.getContent())
                .totalPages(subClassificationDetailsProjections.getTotalPages())
                .totalElements(subClassificationDetailsProjections.getTotalElements())
                .build();
    }


    private static PaginationDto<Object> getEmptyPage() {
        return PaginationDto.builder()
                .content(new ArrayList<>())
                .totalPages(0)
                .totalElements(0L)
                .build();
    }

    public boolean  isTmoHasThisTrademark(String tmoCustomerCode, Long trademarkId){
        List<String> owners = applicationRelevantTypeRepository.findCustomerCodesByApplicationId(trademarkId);
        return owners.contains(tmoCustomerCode) ? true : false;
    }


    @Override
    public PaginationDto getAllSubClassificationsByClassificationId(int page, int limit, String query, long classId) {
        PageRequest pageable = PageRequest.of(page, limit);
        Page<SubClassification> subClassifications = subClassRepository.findByClassificationId(query, classId, pageable);

        List<SubClassificationDto> dtoList = subClassifications.stream()
                .map(entity -> subClassMapper.map(entity))  // You might need to implement this conversion function
                .collect(Collectors.toList());

        PaginationDto result = new PaginationDto();
        result.setContent(dtoList);
        result.setTotalElements(subClassifications.getTotalElements());
        result.setTotalPages(subClassifications.getTotalPages());

        return result;
    }

    private List<SubClassificationDto> mapSubClass (List<SubClassification> subClass, long applicationId, long count , Boolean isShortcut){
        List<Long> subClassIds = subClass.stream().map(BaseEntity::getId).toList();
        List<ApplicationSubClassification> applicationSubClassifications = getAppSubClassSelected(subClassIds, applicationId);
        Map<Long, ApplicationSubClassification> applicationSubClassificationsMap = applicationSubClassifications.stream()
                .collect(Collectors.toMap(applicationSubClassification -> applicationSubClassification.getSubClassification().getId(), Function.identity()));
       return subClass.stream().map(sub -> {
           SubClassificationDto dto = subClassMapper.map(sub);
           Boolean isSubClassSelected = applicationSubClassificationsMap.get(sub.getId()) != null;
           dto.setSelected(count < 1 && isShortcut ? true :  isSubClassSelected);
           return dto;
        }).collect(Collectors.toList());
    }

    public boolean isLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<SubClassification> findByIdIn(List<Long> selectedSubClasss) {
        return subClassRepository.findByIdIn(selectedSubClasss);
    }

    @Override
    public List<SubClassification> findSubClassByIsShortcutAndCategoryId(Long categoryId) {
        return subClassRepository.findSubClassificationByIsShortcutAndClassificationId
                      (true, categoryId);
    }

    @Override
    public boolean checkSubClassSelected(long subClassId, long applicationId) {
        return subClassRepository.checkSubClassSelected(subClassId, applicationId);
    }
    
    @Override
    public List<ApplicationSubClassification> getAppSubClassSelected(List<Long> subClassIds, long applicationId) {
        return subClassRepository.getAppSubClassSelected(subClassIds, applicationId);
    }

    @Autowired()
    public void setApplicationSubClassificationService(@Lazy ApplicationSubClassificationService applicationSubClassificationService) {
        this.applicationSubClassificationService = applicationSubClassificationService;
    }


    @Override
    public SubClassification insert(SubClassification subClassification) {
        Long classificationId = subClassification.getClassification().getId();
        Classification classification = classificationService.findById(classificationId);
        SubClassification newSubClassification = new SubClassification();
        newSubClassification.setDescriptionEn(classification.getDescriptionEn());
        newSubClassification.setDescriptionAr(classification.getDescriptionAr());
        newSubClassification.setNameAr(subClassification.getNameAr());
        newSubClassification.setNameEn(subClassification.getNameEn());
        newSubClassification.setSerialNumberAr(subClassification.getSerialNumberAr());
        newSubClassification.setSerialNumberEn(subClassification.getSerialNumberEn());
        newSubClassification.setClassification(classification);
        newSubClassification.setCode(subClassification.getCode());
        newSubClassification.setEnabled(true);
        newSubClassification.setVisible(true);
        return super.insert(newSubClassification);
    }


    @Override
    public SubClassification update(SubClassification subClassification){
        SubClassification currentSubClassification = getReferenceById(subClassification.getId());
        Long classificationId = subClassification.getClassification().getId();
        Classification classification = classificationService.findById(classificationId);
        currentSubClassification.setDescriptionAr(subClassification.getDescriptionAr());
        currentSubClassification.setDescriptionEn(subClassification.getDescriptionEn());
        currentSubClassification.setNameAr(subClassification.getNameAr());
        currentSubClassification.setNameEn(subClassification.getNameEn());
        currentSubClassification.setSerialNumberAr(subClassification.getSerialNumberAr());
        currentSubClassification.setSerialNumberEn(subClassification.getSerialNumberEn());
        currentSubClassification.setClassification(classification);
        currentSubClassification.setCode(subClassification.getCode());
        return super.update(currentSubClassification);
    }


    @Override
    public PaginationDto revokeProductsSubClassification(int page, int limit, Long classificationId, Long applicationId, String query, Long basicNumber) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id"));
      Page<SubClassification> appSubClassifications = subClassRepository.getSubClassificationByApplicationId(classificationId, applicationId, query , basicNumber, pageable);
      List<ListSubClassificationDto> appSubClassificationsDto = subClassMapper.mapList(appSubClassifications.getContent());
      List<Long> revokedSubClassificationIds = revokeProductsService.getRevokedSubClassificationsIdByApplicationId(applicationId);
      appSubClassificationsDto.forEach(dto -> {
          dto.setShorten(revokedSubClassificationIds.contains(dto.getId()));
        });
        return PaginationDto.builder()
                .content(appSubClassificationsDto)
                .totalPages(appSubClassifications.getTotalPages())
                .totalElements(appSubClassifications.getTotalElements())
                .build();
    }

    @Override
    public void reedLocarnoSheet(MultipartFile file , Map<Long, Classification> classificationMap){


        try (InputStream inputStream = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            // Read data from Sheet2 (sheet at index 0)
            XSSFSheet sheet2 = ((XSSFWorkbook) workbook).getSheetAt(3);

            XSSFRow row = null;

            List<SubClassification> subClassificationList = new ArrayList<>() ;
            SubClassification subClassification = null ;

            for (int i = 1; i <= sheet2.getLastRowNum(); i++) {
                row = sheet2.getRow(i);
                String code = row.getCell(2) == null ? "" : row.getCell(2).toString();
                String nameEn = row.getCell(3) == null ? "" : row.getCell(3).toString();
                String nameAr = row.getCell(4) == null ? "" : row.getCell(4).toString();
                String discription = row.getCell(11) == null ? "" : row.getCell(11).toString();
                String lkClassificationUnitId = row.getCell(7) == null ? null : row.getCell(7).toString();
                if (lkClassificationUnitId != null) {
                    Double lkClassificationUnitValue = Double.parseDouble(lkClassificationUnitId); // old Id 11001.0
                    long lkClassificationUnitLong = lkClassificationUnitValue.longValue();
                    if (classificationMap.containsKey(lkClassificationUnitLong)) {
                        subClassification = new SubClassification();
                        subClassification.setCode(code);
                        subClassification.setNameEn(nameEn);
                        subClassification.setNameAr(nameAr);
                        subClassification.setEnabled(true);
                        subClassification.setVisible(true);
                        subClassification.setClassification(classificationMap.get(lkClassificationUnitLong));
                        subClassification.setNiceVersion(0);
                        subClassification.setDescriptionAr(discription);
                        subClassification.setDescriptionEn(discription);
                        subClassification.setShortcut(false);

                        subClassificationList.add(subClassification);
                    }
                }
            }

            subClassRepository.saveAll(subClassificationList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
