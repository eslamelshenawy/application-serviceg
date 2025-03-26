package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.model.TermsAndConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermsAndConditionsRepository extends JpaRepository<TermsAndConditions, Long> {
    List<TermsAndConditions> findAllByOrderBySortingAsc();
    List<TermsAndConditions> findByApplicationCategoryIdAndRequestTypeIdOrderBySortingAsc(Long applicationCategoryId, Long requestTypeId);
}
