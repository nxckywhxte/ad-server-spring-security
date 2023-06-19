package ru.nxckywhxte.ad.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nxckywhxte.ad.server.entities.enums.Gender;

import java.time.LocalDateTime;
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
    private Enum<Gender> gender;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "created_at")
    @Builder.Default
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Builder.Default
    private Date updatedAt = new Date();
}
