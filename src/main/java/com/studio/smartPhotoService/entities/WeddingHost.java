package com.studio.smartPhotoService.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/*
WeddingHost Entity that holds the details of a Wedding Host
This Object holds the data fetched from wedding_host table
 */

@Entity
@Table(name = "wedding_host")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeddingHost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long weddingHostId;

    @NotEmpty(message = "Wedding host's name must not be empty")
    public String weddingHostName;

    @NotEmpty(message = "Wedding host's email must not be empty")
    public String weddingHostEmailId;

    @NotNull(message = "Wedding date and time must not be null")
    public LocalDateTime weddingDateAndTime;

    @NotEmpty(message = "Wedding bride and groom name must not be empty")
    public String brideWedsGroom;

    public String uniqueId;

}
