package gov.saip.applicationservice.common.mapper.agency;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.BaseStatusChangeLogDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestListDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.model.agency.LkClientType;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyStatusChangeLog;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TrademarkAgencyRequestMapper extends BaseMapper<TrademarkAgencyRequest, TrademarkAgencyRequestDto> {

    List<TrademarkAgencyRequestListDto> mapEntityToTrademarkAgencyRequestListDto(List<TrademarkAgencyRequest> entity);
    List<BaseStatusChangeLogDto> mapToBaseStatusChangeLogDto(List<TrademarkAgencyStatusChangeLog> t);


    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget TrademarkAgencyRequest entity, TrademarkAgencyRequestDto dto) {

        if (!Collections.isEmpty(dto.getDocumentIds())) {
            List<Document> documents = dto.getDocumentIds().stream().map(id -> new Document(id)).collect(Collectors.toList());
            entity.setDocuments(documents);
        }

        if (!Collections.isEmpty(dto.getSupportServiceIds())) {
            List<LKSupportServices> services = dto.getSupportServiceIds().stream().map(id -> new LKSupportServices(id)).collect(Collectors.toList());
            entity.setSupportServices(services);
        }

        if (dto.getClientTypeId() != null) {
            entity.setClientType(new LkClientType(dto.getClientTypeId()));
        }

        if (dto.getApplicationId() != null && dto.getApplicationId() != 0) {
            entity.setApplication(new ApplicationInfo(dto.getApplicationId()));
        } else {
            entity.setApplication(null);
        }
    }




    @AfterMapping
    default void afterMapToDto(TrademarkAgencyRequest entity, @MappingTarget TrademarkAgencyRequestDto dto) {

        if (entity.getDocuments() != null) {
            List<Long> documents = entity.getDocuments().stream().map(doc -> doc.getId()).collect(Collectors.toList());
            dto.setDocumentIds(documents);
        }

        if (!Collections.isEmpty(entity.getSupportServices())) {
            List<Long> services = entity.getSupportServices().stream().map(ser -> ser.getId()).collect(Collectors.toList());
            dto.setSupportServiceIds(services);
        }

        if (entity.getClientType() != null) {
            dto.setClientTypeId(entity.getClientType().getId());
        }

        if (entity.getApplication() != null) {
            dto.setApplicationId(entity.getApplication().getId());
        }
    }
}
