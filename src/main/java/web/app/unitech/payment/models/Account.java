package web.app.unitech.payment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import web.app.unitech.user.models.User;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String accountNumber;

    private double balance;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public Account(User testUser, boolean b) {
        this.user = testUser;
        this.active = b;
    }

    public Account(User testUser, boolean b, String accountNumber, double v) {
        this.user = testUser;
        this.active = b;
        this.accountNumber = accountNumber;
        this.balance = v;
    }
}
