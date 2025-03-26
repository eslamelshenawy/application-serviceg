package gov.saip.applicationservice.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseLkRepository<T, ID> extends JpaRepository<T, ID> {

   Optional<T> findByCode(String code);

}
