package hcmus.alumni.search.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import hcmus.alumni.search.dto.ISearchDto;
import hcmus.alumni.search.model.UserModel;

public interface SearchRepository extends JpaRepository<UserModel, String> {

	@Query("SELECT DISTINCT u " + 
		   "FROM UserModel u " + 
		   "LEFT JOIN u.status s " + 
		   "LEFT JOIN u.faculty f " + 
		   "WHERE (:statusId IS NULL OR s.id = :statusId) " + 
		   "AND (:facultyId IS NULL OR f.id = :facultyId) " + 
		   "AND (:beginningYear IS NULL OR u.beginningYear = :beginningYear) " + 
		   "AND s.id != 4 " +
		   "AND u.fullName LIKE %:fullName%")
	Page<ISearchDto> searchUsers(String fullName, Integer statusId,
			Integer facultyId, Integer beginningYear, Pageable pageable);

	@Query("SELECT COUNT(n) FROM UserModel n JOIN n.status s WHERE s.name = :statusName")
	Long getCountByStatus(@Param("statusName") String statusName);

}