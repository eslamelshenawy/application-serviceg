package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.model.SearchedTrademark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchedTrademarkRepository extends JpaRepository<SearchedTrademark, Long> {

    // Find by applicationInfoId
    List<SearchedTrademark> findByApplicationInfoId(Long applicationInfoId);

    // Find by request number (to prevent duplicates)
    boolean existsByRequestNumber(String requestNumber);
    void deleteById(Long id);
    boolean existsById(Long id);
}
