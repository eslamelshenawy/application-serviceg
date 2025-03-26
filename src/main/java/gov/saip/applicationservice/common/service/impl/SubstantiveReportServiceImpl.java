package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.SubstantiveReportDto;
import gov.saip.applicationservice.common.enums.ExaminerReportType;
import gov.saip.applicationservice.common.mapper.SubstantiveReportMapper;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.SubstantiveExaminationReport;
import gov.saip.applicationservice.common.repository.DocumentRepository;
import gov.saip.applicationservice.common.repository.SubstantiveReportRepository;
import gov.saip.applicationservice.common.service.SubstantiveReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class SubstantiveReportServiceImpl extends BaseServiceImpl<SubstantiveExaminationReport, Long> implements SubstantiveReportService {

    private final SubstantiveReportRepository substantiveReportRepository;
    private final DocumentRepository documentRepository;
    private final SubstantiveReportMapper substantiveReportMapper;

    @Override
    protected BaseRepository<SubstantiveExaminationReport, Long> getRepository() {
        return substantiveReportRepository;
    }
    @Override
    public List<SubstantiveReportDto> getAllByApplicationId(Long appId, ExaminerReportType type) {
        List<SubstantiveReportDto> substantiveReportDtoList = substantiveReportMapper.map(substantiveReportRepository.findByApplicationInfoId(appId, type));

        if(ExaminerReportType.SUBSTANTIVE_EXAMINER == type) {
            if(substantiveReportDtoList.isEmpty())
                substantiveReportDtoList.add(new SubstantiveReportDto());

            List<SubstantiveExaminationReport> substantiveCheckerReportList = substantiveReportRepository.findByApplicationInfoId(appId, ExaminerReportType.SUBSTANTIVE_CHECKER);
            substantiveReportDtoList.get(0).setExaminerReport(substantiveCheckerReportList.get(0).getExaminerOpinion());
            substantiveReportDtoList.get(0).setType(ExaminerReportType.SUBSTANTIVE_EXAMINER);
            substantiveReportDtoList.get(0).setApplicationId(appId);

        }

        return substantiveReportDtoList;
    }

    @Override
    public SubstantiveReportDto getLastAcceptWithConditionReportByApplicationId(Long appId) {
        List<SubstantiveExaminationReport> substantiveExaminationReportList = substantiveReportRepository.getAcceptWithConditionReportByApplicationId(appId, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id")));
        return substantiveExaminationReportList != null && !substantiveExaminationReportList.isEmpty() ? substantiveReportMapper.map(substantiveExaminationReportList.get(0)) : null;
    }

    @Override
    public SubstantiveExaminationReport insert(SubstantiveExaminationReport entity) {
        List<SubstantiveExaminationReport> reports = substantiveReportRepository.findByApplicationInfoId(entity.getApplicationInfo().getId(), entity.getType());
        if (reports.isEmpty()) {
            return super.insert(entity);
        } else {
            SubstantiveExaminationReport current = reports.get(0);
            current.setLinks(entity.getLinks());
            current.setExaminerOpinion(entity.getExaminerOpinion());
            current.setExaminerRecommendation(entity.getExaminerRecommendation());
            current.setDocuments(entity.getDocuments() != null ? collectDocument(entity.getDocuments()) : null);
            current.setDecision(entity.getDecision());
            return super.update(current);
        }
    }



    @Override
    public SubstantiveExaminationReport update(SubstantiveReportDto substantiveReportDto) {
        SubstantiveExaminationReport substantiveExaminationReport = insert(substantiveReportMapper.unMap(substantiveReportDto));

//        if(ExaminerReportType.SUBSTANTIVE_EXAMINER == substantiveReportDto.getType()) {
//            substantiveReportRepository.updateExaminerOpinion(substantiveReportDto.getApplicationId(), ExaminerReportType.SUBSTANTIVE_CHECKER, substantiveReportDto.getExaminerReport());
//        }

        return substantiveExaminationReport;
    }


    private List<Document> collectDocument(List<Document>  documentList) {
        return documentList.stream().map(document -> documentRepository.getReferenceById(document.getId())).toList();
    }
}
