package hiof.parking.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String city;
    private String address;
    private int number;
    private int zipcode;
    private String area;

    public Location(String city, String address, int number, int zipcode, String area) {
        this.city = city;
        this.address = address;
        this.number = number;
        this.zipcode = zipcode;
        this.area = area;
    }
}