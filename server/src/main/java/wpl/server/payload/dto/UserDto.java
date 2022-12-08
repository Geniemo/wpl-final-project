package wpl.server.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {

    private Long userId;
    private String email;
    private String name;
    private List<SolveDto> solves = new ArrayList<>();
}
