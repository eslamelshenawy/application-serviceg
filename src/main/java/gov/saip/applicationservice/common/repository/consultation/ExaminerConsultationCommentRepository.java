package gov.saip.applicationservice.common.repository.consultation;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ExaminerConsultation;
import gov.saip.applicationservice.common.model.ExaminerConsultationComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminerConsultationCommentRepository extends BaseRepository<ExaminerConsultationComment, Long> {


}
