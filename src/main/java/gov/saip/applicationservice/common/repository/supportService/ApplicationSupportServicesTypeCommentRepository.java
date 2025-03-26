package gov.saip.applicationservice.common.repository.supportService;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.supportService.ApplicationSupportServicesTypeComment;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationSupportServicesTypeCommentRepository extends BaseRepository<ApplicationSupportServicesTypeComment, Long> {

    List<ApplicationSupportServicesTypeComment> findAllByApplicationSupportServicesTypeId(Long applicationSupportServicesTypeId);

    List<ApplicationSupportServicesTypeComment> findLastByApplicationSupportServicesTypeId(Long applicationSupportServicesTypeId, Pageable pageable);

}
