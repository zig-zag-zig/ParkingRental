package hiof.parking.repository;
import hiof.parking.model.Parkinglot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkinglotRepo extends JpaRepository<Parkinglot, Long> {
}