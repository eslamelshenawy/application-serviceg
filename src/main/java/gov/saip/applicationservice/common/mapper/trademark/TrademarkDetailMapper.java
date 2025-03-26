package gov.saip.applicationservice.common.mapper.trademark;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.trademark.*;
import gov.saip.applicationservice.common.model.trademark.TrademarkDetail;
import gov.saip.applicationservice.common.projection.TradeMarkInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(imports = {LkMarkTypeMapper.class, LkTagTypeDescMapper.class, LkTagLanguageMapper.class})
public interface TrademarkDetailMapper extends BaseMapper<TrademarkDetail, TrademarkDetailDto> {

    @Mapping(target = "markType", ignore = true)
    @Mapping(target = "tagTypeDesc", ignore = true)
    @Mapping(target = "tagLanguage", ignore = true)
    @Mapping(target = "id", ignore = true)
    TrademarkDetail mapDetReq(TrademarkDetailReqDto req);

    @Mapping(target = "markType", ignore = true)
    @Mapping(target = "tagTypeDesc", ignore = true)
    @Mapping(target = "tagLanguage", ignore = true)
    @Mapping(target = "id", ignore = true)
    TrademarkDetail mapDetReq(TrademarkDetailReqDto req, @MappingTarget TrademarkDetail entity);

    ApplicationTrademarkDetailDto mapApplicationDet(TrademarkDetail e);
    
    ApplicationTrademarkDetailLightDto mapTrademarkApplicationDetails(TradeMarkInfo trademarkDetails);
    
    List<ApplicationTrademarkDetailLightDto> mapTrademarkApplicationDetails(List<TradeMarkInfo> trademarkDetails);
    
    @Mappings({
            @Mapping(source = "tagLanguage.nameAr", target = "tagLanguageNameAr"),
            @Mapping(source = "tagLanguage.nameEn", target = "tagLanguageNameEn"),
            @Mapping(source = "markType.nameAr", target = "typeNameAr"),
            @Mapping(source = "markType.nameEn", target = "typeNameEn"),
            @Mapping(source = "tagTypeDesc.nameAr", target = "tagTypeNameAr"),
            @Mapping(source = "tagTypeDesc.nameEn", target = "tagTypeNameEn"),
            @Mapping(source = "tagTypeDesc.code", target = "tagTypeCode")
        
        
    })
    TradeMarkLightDto mapTrademarkLight(TrademarkDetailDto trademarkDetailDto);


    @Mappings({

            @Mapping(source = "appId", target = "id"),
            @Mapping(source = "markClaimingColor", target = "tradeMarkDetails.markClaimingColor"),
            @Mapping(source = "typeNameAr", target = "tradeMarkDetails.typeNameAr"),
            @Mapping(source = "typeNameEn", target = "tradeMarkDetails.typeNameEn"),
            @Mapping(source = "tagLanguageNameAr", target = "tradeMarkDetails.tagLanguageNameAr"),
            @Mapping(source = "tagLanguageNameEn", target = "tradeMarkDetails.tagLanguageNameEn"),
            @Mapping(source = "tagTypeNameAr", target = "tradeMarkDetails.tagTypeNameAr"),
            @Mapping(source = "tagTypeNameEn", target = "tradeMarkDetails.tagTypeNameEn"),
            @Mapping(source = "markDescription", target = "tradeMarkDetails.markDescription"),
            @Mapping(source = "examinerGrantCondition", target = "tradeMarkDetails.examinerGrantCondition"),
            @Mapping(source = "tmNameEn", target = "tradeMarkDetails.nameEn"),
            @Mapping(source = "tmNameAr", target = "tradeMarkDetails.nameAr"),

            @Mapping(source = "statusId", target = "applicationStatus.id"),
            @Mapping(source = "statusAr", target = "applicationStatus.ipsStatusDescArExternal"),
            @Mapping(source = "statusEn", target = "applicationStatus.ipsStatusDescEnExternal"),
            @Mapping(source = "statusInternalAr", target = "applicationStatus.ipsStatusDescAr"),
            @Mapping(source = "statusInternalEn", target = "applicationStatus.ipsStatusDescEn"),
            @Mapping(source = "statusCode", target = "applicationStatus.code"),

            @Mapping(source = "categoryId", target = "category.id"),
            @Mapping(source = "categoryDescAr", target = "category.applicationCategoryDescAr"),
            @Mapping(source = "categoryDescEn", target = "category.applicationCategoryDescEn"),
            @Mapping(source = "categoryCode", target = "category.saipCode"),


    })
    ApplicationTrademarkDetailSummaryDto mapSummaryProjectionToDto(ApplicationTradeMarkProjection projection);

}
