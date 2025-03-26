package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.enums.ExaminerReportType;
import gov.saip.applicationservice.common.model.SubstantiveExaminationReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubstantiveReportRepository extends BaseRepository<SubstantiveExaminationReport, Long> {

    @Query("select report from SubstantiveExaminationReport report" +
            " join report.applicationInfo appInfo" +
            " where" +
            " appInfo.id = :appId" +
            " and (:type is null or report.type = :type)")
    List<SubstantiveExaminationReport> findByApplicationInfoId(@Param("appId") Long appId, @Param("type") ExaminerReportType type );

    @Query("select report from SubstantiveExaminationReport report where report.applicationInfo.id = :appId" +
            " and report.decision = 'GRANT_CONDITION'")
    List<SubstantiveExaminationReport> getAcceptWithConditionReportByApplicationId(Long appId, Pageable pageable);

    @Modifying
    @Query("update SubstantiveExaminationReport set examinerOpinion = :examinerOpinion where applicationInfo.id = :applicationInfoId and type = :type")
    void updateExaminerOpinion(@Param("applicationInfoId") Long applicationInfoId, @Param("type") ExaminerReportType type, @Param("examinerOpinion") String examinerOpinion);
}
