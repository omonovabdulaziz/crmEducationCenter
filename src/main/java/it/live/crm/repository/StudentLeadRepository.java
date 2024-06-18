package it.live.crm.repository;

import it.live.crm.entity.StudentLead;
import it.live.crm.payload.ExpectationStudentDTO;
import it.live.crm.payload.StudentGetLeadDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentLeadRepository extends JpaRepository<StudentLead, Long> {
    @Query(value = "SELECT new it.live.crm.payload.StudentGetLeadDTO(sl.student.fullName , sl.whereLink.id , sl.comment , sl.student.phoneNumber , sl.id) from StudentLead as sl  where sl.whereLink.id=:linkId")
    List<StudentGetLeadDTO> findAllByWhereLinkId(@Param("linkId") Long linkId);

    @Modifying
    void deleteByStudentId(Long studentId);

    @Query(value = "select new it.live.crm.payload.ExpectationStudentDTO(s.fullName , s.phoneNumber , s.id) from Student s where s.expectations.id=:exId")
    List<ExpectationStudentDTO> getStudentByExpectation(@Param("exId") Long exId);


}
