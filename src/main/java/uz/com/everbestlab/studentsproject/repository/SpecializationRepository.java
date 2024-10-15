package uz.com.everbestlab.studentsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.everbestlab.studentsproject.model.entity.Specialization;

import java.util.UUID;
@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, UUID> {
    @Query("select u from specialization as u where u.isDeleted=false and u.id=?1")
    Specialization getSpecializationById(UUID id);
}
