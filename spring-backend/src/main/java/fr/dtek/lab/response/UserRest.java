package fr.dtek.lab.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRest {
    private String userFirstName;
    private String userLastName;
    private String userId;
}