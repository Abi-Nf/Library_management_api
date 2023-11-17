package models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Subscribers extends User {
    private String reference;
    private String password;
}
