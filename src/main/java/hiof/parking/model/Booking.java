package hiof.parking.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;
import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Parkingspot_id", referencedColumnName = "id")
    private Parkingspot spot;
    @ElementCollection
    private List<String> dateAndTime;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Parkinglot_id", referencedColumnName = "id")
    private Parkinglot parkinglot;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_id", referencedColumnName = "id")
    private User user;
    private int price;

    public Booking(Parkingspot spot, ArrayList<String> dateAndTime, Parkinglot parkinglot, User user, int price) {
        this.spot = spot;
        this.dateAndTime = dateAndTime;
        this.parkinglot = parkinglot;
        this.user = user;
        this.price = price;
    }
}

