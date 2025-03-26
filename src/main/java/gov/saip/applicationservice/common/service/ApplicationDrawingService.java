package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationDrawingDto;
import gov.saip.applicationservice.common.model.ApplicationDrawing;

import java.util.List;

public interface ApplicationDrawingService extends BaseService<ApplicationDrawing, Long> {

    //ApplicationDrawing saveAppDrawing(ApplicationDrawing entity);

    List<ApplicationDrawing> getAppDrawing(Long appId);

    boolean checkMainDrawExists(Long appId);

    void deleteByAppAndDocumentId(Long appId, Long documentId);
    void deleteById(List<Long> appId);

    List<Long> createApplicationDrawings(List<ApplicationDrawingDto> requestList);
}
