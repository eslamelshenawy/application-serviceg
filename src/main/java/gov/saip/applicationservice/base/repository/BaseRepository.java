package gov.saip.applicationservice.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    @Modifying
    @Transactional
    @Query("update #{#entityName} t SET t.isDeleted = :isDeleted WHERE t.id = :id")
    int updateIsDeleted(@Param("id") ID id, @Param("isDeleted") boolean isDeleted);

    @Modifying
    @Transactional
    @Query("update #{#entityName} t SET t.isDeleted = :isDeleted WHERE t.id = :id")
    int updateIsDeleted(@Param("id") ID id, @Param("isDeleted") int isDeleted);

    @Modifying
    @Transactional
    @Query("delete from #{#entityName} t WHERE t.id = :id")
    int hardDeleteById(@Param("id") ID id);

}
