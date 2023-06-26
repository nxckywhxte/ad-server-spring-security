package ru.nxckywhxte.ad.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ru.nxckywhxte.ad.server.entities.enums.Gender;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "_profiles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne()
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "created_at")
    @Builder.Default
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Builder.Default
    private Date updatedAt = new Date();
}
