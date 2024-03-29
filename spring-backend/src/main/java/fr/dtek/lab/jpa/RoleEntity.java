package fr.dtek.lab.jpa;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class RoleEntity {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "id", columnDefinition = "VARCHAR(255)", insertable = false, updatable = false, nullable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;
}
