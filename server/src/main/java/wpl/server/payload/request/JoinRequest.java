package wpl.server.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JoinRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;
}
