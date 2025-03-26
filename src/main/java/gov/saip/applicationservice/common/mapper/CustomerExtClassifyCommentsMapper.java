package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.CustomerExtClassifyCommentsDto;
import gov.saip.applicationservice.common.model.CustomerExtClassifyComments;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerExtClassifyCommentsMapper extends BaseMapper<CustomerExtClassifyComments, CustomerExtClassifyCommentsDto> {
    @Override
    @Mappings({
            @Mapping(source = "parentCustExtComment.id", target = "custExtClassifyParentCommentId"),
            @Mapping(source = "customerExtClassify.id", target = "custExtClassifyId")
    })
    CustomerExtClassifyCommentsDto map(CustomerExtClassifyComments customerExtClassifyComments);

    @Override
    @Mappings({
            @Mapping(source = "custExtClassifyParentCommentId", target = "parentCustExtComment.id"),
            @Mapping(source = "custExtClassifyId", target = "customerExtClassify.id")
    })
    CustomerExtClassifyComments unMap(CustomerExtClassifyCommentsDto customerExtClassifyCommentsDto);

    @AfterMapping
    default void afterMappingData(@MappingTarget CustomerExtClassifyComments entity, CustomerExtClassifyCommentsDto dto) {
        if (dto.getCustExtClassifyParentCommentId() == null) {
            entity.setParentCustExtComment(null);
        }
        if (dto.getCustExtClassifyId() == null || dto.getCustExtClassifyParentCommentId() != null) {
            entity.setCustomerExtClassify(null);
        }
    }
}
