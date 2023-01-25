package ro.srbrasov.volunteer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.srbrasov.volunteer.entity.Volunteer;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Volunteer findByUserId(Long userId);
}
