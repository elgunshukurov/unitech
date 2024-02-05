package web.app.unitech.user.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.app.unitech.payment.models.Account;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String pin;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Account> accounts;

    public User(String johnDoe, String pin, String password, HashSet<Role> es) {
        this.name = johnDoe;
        this.pin = pin;
        this.password = password;
        this.roles = es;
    }

    public User(String johnDoe, String pin, String password) {
        this.name = johnDoe;
        this.pin = pin;
        this.password = password;
    }
}
