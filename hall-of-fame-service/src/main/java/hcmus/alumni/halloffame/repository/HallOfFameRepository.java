package hcmus.alumni.halloffame.repository;

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

import hcmus.alumni.halloffame.dto.IHallOfFameDto;
import hcmus.alumni.halloffame.model.HallOfFameModel;

public interface HallOfFameRepository extends JpaRepository<HallOfFameModel, String> {
	Optional<IHallOfFameDto> findHallOfFameById(String id);

	Optional<HallOfFameModel> findById(String id);

	@Query("SELECT h " + "FROM HallOfFameModel h " + " JOIN h.status s " + " JOIN h.faculty f "
			+ "WHERE (:statusId IS NULL OR s.id = :statusId) " + "AND (:facultyId IS NULL OR f.id = :facultyId) "
			+ "AND (:beginningYear IS NULL OR h.beginningYear = :beginningYear) " + "AND h.title LIKE %:title%")
	Page<IHallOfFameDto> searchHof(@Param("title") String title, @Param("statusId") Integer statusId,
			@Param("facultyId") Integer facultyId, @Param("beginningYear") Integer beginningYear, Pageable pageable);

	@Query("SELECT COUNT(n) FROM HallOfFameModel n JOIN n.status s WHERE s.name = :statusName")
	Long getCountByStatus(@Param("statusName") String statusName);

	@Query("SELECT COUNT(n) FROM HallOfFameModel n JOIN n.status s WHERE s.id != 4")
	Long getCountByNotDelete();

	@Query("SELECT n.id FROM UserModel n WHERE n.email = :emailOfUser")
	String getUserIdByEmail(@Param("emailOfUser") String emailOfUser);

	@Transactional
	@Modifying
	@Query("UPDATE HallOfFameModel n SET n.views = n.views + 1 WHERE n.id = :id")
	int viewsIncrement(String id);

	@Query("SELECT n from HallOfFameModel n JOIN n.status s WHERE s.name = \"Chờ\" AND n.publishedAt <= :now")
	List<HallOfFameModel> getScheduledHof(Date now);
}