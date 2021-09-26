package hiof.parking.repository;

import hiof.parking.model.Parkingspot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingspotRepo extends JpaRepository<Parkingspot, Long> {
}
