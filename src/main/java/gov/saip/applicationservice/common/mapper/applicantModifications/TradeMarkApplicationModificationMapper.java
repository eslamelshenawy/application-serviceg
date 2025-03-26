package gov.saip.applicationservice.common.mapper.applicantModifications;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.applicantModifications.TradeMarkApplicationModificationDto;
import gov.saip.applicationservice.common.model.TrademarkApplicationModification;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.trademark.TrademarkDetail;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Objects;

@Mapper
public interface TradeMarkApplicationModificationMapper extends BaseMapper<TradeMarkApplicationModificationDto, TrademarkApplicationModification> {
    @Override
    @Mapping(source = "nameAr", target = "newMarkNameAr")
    @Mapping(source = "nameEn", target = "newMarkNameEn")
    @Mapping(source = "markTypeId", target = "newMarkType")
    @Mapping(source = "tagTypeDesc", target = "newMarkTypeDesc")
    @Mapping(source = "markDescription", target = "newMarkDesc")
    @Mapping(source = "tagLanguageId", target = "newTagLanguageId")
    TrademarkApplicationModification map(TradeMarkApplicationModificationDto tradeMarkApplicationModificationDto);
    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget TrademarkApplicationModification entity, TradeMarkApplicationModificationDto dto) {


        if (Objects.nonNull(dto.getAppId())) {
            entity.setApplication(new ApplicationInfo(dto.getAppId()));
        }
    }

    default TrademarkApplicationModification mapOldTradeMarkValues(TrademarkApplicationModification modifications, TrademarkDetail trademarkDetail) {
        if (Objects.nonNull(modifications) && Objects.nonNull(trademarkDetail)) {
            modifications.setOldMarkNameAr(trademarkDetail.getNameAr() == null ? null : trademarkDetail.getNameAr());
            modifications.setOldMarkNameEn(trademarkDetail.getNameEn() == null ? null : trademarkDetail.getNameEn());
            modifications.setOldMarkDesc(trademarkDetail.getMarkDescription() == null ? null : trademarkDetail.getMarkDescription());
            if (Objects.nonNull(trademarkDetail.getTagTypeDesc())) {
                modifications.setOldMarkTypeDesc(trademarkDetail.getTagTypeDesc().getId());
            }
            if (Objects.nonNull(trademarkDetail.getTagLanguage())) {
                modifications.setOldTagLanguageId(trademarkDetail.getTagLanguage().getId());
            }
            if(Objects.nonNull(trademarkDetail.getMarkType())){
                modifications.setOldMarkType(trademarkDetail.getMarkType().getId());

            }
            return modifications;
        }
        return null;
    }
}
