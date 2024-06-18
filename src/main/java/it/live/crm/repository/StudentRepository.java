package it.live.crm.repository;

import it.live.crm.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT COUNT(s) FROM Student s WHERE s.expectations.id = :expectationsId")
    Long countByExpectationsId(@Param("expectationsId") Long expectationsId);

    @Modifying
    void deleteAllByExpectationsId(Long expectationsId);

    Long countByGroupId(Long groupId);

    List<Student> findAllByGroupIdAndGroup_IsGroup(Long group_id, Boolean group_isGroup);
    @Modifying
    void deleteAllByGroupIdAndGroup_IsGroup(Long group_id, Boolean group_isGroup);


}