package web.app.unitech.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpPayload {

    private String name;
    private String pin;
    private String password;

}
