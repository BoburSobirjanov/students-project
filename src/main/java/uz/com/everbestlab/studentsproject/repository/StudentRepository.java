package uz.com.everbestlab.studentsproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.everbestlab.studentsproject.model.entity.Student;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    @Query("select u from students as u where u.id=?1 and u.isDeleted=false")
    Student getStudentById(UUID id);


    @Query("select u from students as u where u.isDeleted=false")
    Page<Student> findAllStudents(Pageable pageable);

    @Query("select u from students as u where u.isDeleted=false")
    List<Student> getAllStudentsToExcel();
}
