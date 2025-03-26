package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.ApplicationDrawingDto;
import gov.saip.applicationservice.common.model.ApplicationDrawing;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.repository.ApplicationDrawingRepository;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.DocumentRepository;
import gov.saip.applicationservice.common.service.ApplicationDrawingService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationDrawingServiceImpl extends BaseServiceImpl<ApplicationDrawing, Long> implements ApplicationDrawingService {

    private final ApplicationDrawingRepository applicationDrawingRepository;
    private final DocumentsService documentsService;
    private final ApplicationInfoRepository applicationInfoRepository;
    private final DocumentRepository documentRepository;

    @Override
    protected BaseRepository<ApplicationDrawing, Long> getRepository() {
        return applicationDrawingRepository;
    }
    @Override
    public ApplicationDrawing insert(ApplicationDrawing entity) {
        if(checkMainDrawExists(entity.getApplicationInfo().getId()) && entity.isDefault()){
            throw new BusinessException(Constants.ErrorKeys.MAIN_DRAW_IS_EXISTS, HttpStatus.BAD_REQUEST, null);
        }
        return super.insert(entity);
    }

    @Override
    public List<ApplicationDrawing> getAppDrawing(Long appId) {
       return applicationDrawingRepository.getAppDrawing(appId);
    }

    @Override
    public boolean checkMainDrawExists(Long appId) {
         return applicationDrawingRepository.checkMainDrawExists(appId).size() > 0;
    }

    @Override
    @Transactional
    public void deleteByAppAndDocumentId(Long appId, Long documentId) {
        applicationDrawingRepository.deleteByAppAndDocumentId(appId, documentId);
        documentsService.hardDeleteDocumentById(documentId);
        //should delete from nexuo server as well
    }

    @Override
    @Transactional
    public void deleteById(List<Long> appId) {
        applicationDrawingRepository.deleteById(appId);
    }

    @Override
    public List<Long> createApplicationDrawings(List<ApplicationDrawingDto> requestList) {
        List<ApplicationDrawing> applicationDrawings = new ArrayList<>();

        for (ApplicationDrawingDto request : requestList) {
            ApplicationDrawing drawing = new ApplicationDrawing();

            ApplicationInfo applicationInfo = applicationInfoRepository.findById(request.getApplicationId())
                    .orElseThrow(() -> new BusinessException("Invalid application ID: " + request.getApplicationId()));
            Document document = documentRepository.findById(request.getDocument().getId())
                    .orElseThrow(() -> new BusinessException("Invalid document ID: " + request.getDocument().getId()));

            drawing.setApplicationInfo(applicationInfo);
            drawing.setDocument(document);
            drawing.setTitle(request.getTitle());
            drawing.setDefault(request.isDefault());
            drawing.setNumbering(request.getNumbering());
            applicationDrawings.add(drawing);
        }
         // Save all the drawings and map to their IDs
        return applicationDrawingRepository.saveAll(applicationDrawings).stream().map(ApplicationDrawing::getId)
                .collect(Collectors.toList());
    }
}