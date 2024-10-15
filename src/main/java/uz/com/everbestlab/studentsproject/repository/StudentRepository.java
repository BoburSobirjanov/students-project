package uz.com.everbestlab.studentsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.everbestlab.studentsproject.model.entity.Student;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    @Query("select u from students as u where u.id=?1 and u.isDeleted=false")
    Student getStudentById(UUID id);
}
