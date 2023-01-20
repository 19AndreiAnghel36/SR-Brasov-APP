package ro.srbrasov.volunteer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.srbrasov.volunteer.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
