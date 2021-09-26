package hiof.parking.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CascadeType;
import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String username;
    private String password;
    private String firstname;
    private String surname;
    @OneToOne
    @JoinColumn(name = "Location_id", referencedColumnName = "id")
    @Cascade(CascadeType.ALL)
    private Location location;
    @Enumerated(EnumType.STRING)
    private ROLE role;


    public User(String username, String password, String firstname, String surname, Location location, ROLE role) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.surname = surname;
        this.location = location;
        this.role = role;
    }
}
