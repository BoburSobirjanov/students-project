package uz.com.everbestlab.studentsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.everbestlab.studentsproject.model.entity.Student;
import uz.com.everbestlab.studentsproject.model.entity.University;

import java.util.UUID;
@Repository
public interface UniversityRepository extends JpaRepository<University, UUID> {

    @Query("select u from university as u where u.id=?1 and u.isDeleted=false")
    University getUniversityById(UUID id);
}
