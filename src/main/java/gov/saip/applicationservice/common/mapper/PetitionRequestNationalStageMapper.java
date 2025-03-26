package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.PetitionRequestNationalStageDto;
import gov.saip.applicationservice.common.dto.opposition.OppositionRequestDto;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.PetitionRequestNationalStage;
import gov.saip.applicationservice.common.model.opposition.OppositionRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;


@Mapper
public interface PetitionRequestNationalStageMapper extends BaseMapper<PetitionRequestNationalStage, PetitionRequestNationalStageDto> {



    @AfterMapping
    default void afterMapDtoToEntity(PetitionRequestNationalStageDto dto, @MappingTarget PetitionRequestNationalStage entity) {
        if (dto.getDocumentIds() != null && !dto.getDocumentIds().isEmpty()) {
            List<Document> documents = dto.getDocumentIds().stream()
                    .map(id -> {
                        Document doc = new Document();
                        doc.setId(id);
                        return doc;
                    })
                    .collect(Collectors.toList());
            entity.setPetitionRequestNationalStageDocuments(documents);
        }
    }
}
