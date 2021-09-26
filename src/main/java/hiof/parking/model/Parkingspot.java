package hiof.parking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Parkingspot {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @ElementCollection
    @SortNatural
    private SortedMap<String, Boolean> schedule = new ConcurrentSkipListMap<String, Boolean>();
    @Enumerated(EnumType.STRING)
    private TYPE type;
    private int hourlyPrice;
    @JsonIgnore
    private long parkinglotId;

    public Parkingspot(TYPE type, int hourlyPrice) {
        this.type = type;
        this.hourlyPrice = hourlyPrice;
    }

    @Override
    public String toString() {
        return String.format("Spot-ID: " + this.getId() + ", Type: " + this.getType() + ", Price per hour: " + this.getHourlyPrice());
    }
}
