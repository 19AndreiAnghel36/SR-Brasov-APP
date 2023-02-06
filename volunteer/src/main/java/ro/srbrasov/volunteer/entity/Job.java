package ro.srbrasov.volunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "works")
@Entity(name = "works")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "job")
    private Set<User> users;
    @Column(name = "volunteers_count")
    private long volunteersCount;
    @Column(name = "max_volunteers")
    private long maxVolunteers;
}
