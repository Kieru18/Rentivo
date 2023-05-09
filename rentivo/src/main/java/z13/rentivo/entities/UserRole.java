package z13.rentivo.entities;


import javax.persistence.*;

import java.util.Date;

import lombok.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "role_id")
    private Long roleId;
    
    @NonNull
    private String name;
}