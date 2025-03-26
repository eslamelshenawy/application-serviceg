package gov.saip.applicationservice.common.mapper.appeal;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.appeal.TrademarkAppealRequestDto;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.appeal.TrademarkAppealRequest;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper
public interface TrademarkAppealRequestMapper extends BaseMapper<TrademarkAppealRequest, TrademarkAppealRequestDto> {


    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget TrademarkAppealRequest entity, TrademarkAppealRequestDto dto) {

        if (!Collections.isEmpty(dto.getDocumentIds())) {
            List<Document> documents = dto.getDocumentIds().stream().map(id -> new Document(id)).collect(Collectors.toList());
            entity.setDocuments(documents);
        }

        if (dto.getApplicationId() != null) {
            entity.setApplicationInfo(new ApplicationInfo(dto.getApplicationId()));
        }

        if (dto.getSupportServiceId() != null) {
            entity.setSupportServicesType(new ApplicationSupportServicesType(dto.getSupportServiceId()));
        }

        entity.setPaymentStatus(SupportServicePaymentStatus.UNPAID);

        if (Objects.nonNull(dto.getApplicationId())) {
            entity.setApplicationInfo(new ApplicationInfo(dto.getApplicationId()));
        }
    }


}
