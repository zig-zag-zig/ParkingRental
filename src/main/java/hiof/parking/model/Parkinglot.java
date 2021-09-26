package hiof.parking.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Parkinglot {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn(name = "Location_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Location location;
    @OneToMany
    private List<Parkingspot> spots = new ArrayList<Parkingspot>();
    @OneToOne
    @JoinColumn(name = "User_id", referencedColumnName = "id")
    private User owner;

    public Parkinglot(Location location, User owner) {
        this.location = location;
        this.owner = owner;
    }

    @Override
    public String toString() {
        //to avoid nullpointerexception when the parkinglot doesn't have any parkingspots inside its ArrayList of parkingspots
        if (this.getSpots() == null)
            return String.format("Parkinglot ID: " + this.getId() + " " + this.getLocation().toString() + ", Owner: " + this.getOwner());

        return String.format("Parkinglot ID: " + this.getId() + " " + this.getLocation().toString() + " " + this.getSpots().toString() + ", Owner: " + this.getOwner());
    }
}