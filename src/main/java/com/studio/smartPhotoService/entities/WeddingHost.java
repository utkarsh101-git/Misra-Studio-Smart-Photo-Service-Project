package com.studio.smartPhotoService.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.studio.smartPhotoService.exceptions.ShouldNotOccurException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
@ToString
public class WeddingHost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long weddingHostId;

    @NotEmpty(message = "Wedding host's name must not be empty")
    private String weddingHostName;

    @NotEmpty(message = "Wedding host's email must not be empty")
    private String weddingHostEmailId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weddingHost")
    @JsonManagedReference("WeddingHostWeddingObjects")
    private Set<Wedding> createdWeddingSet = new HashSet<>();

//    public void addWeddingObj(Wedding wedding) {
//        if(createdWeddingSet.contains(wedding)){
//            throw new ShouldNotOccurException("WeddingHost already has this %s wedding in its Created Wedding Set".formatted(wedding.getWeddingTitle()));
//        }
//        this.createdWeddingSet.add(wedding);
//        wedding.setWeddingHost(this); // Set the back reference
//    }
//
//    public void removeWeddingObj(Wedding wedding) {
//        if(!createdWeddingSet.contains(wedding)){
//            throw new ShouldNotOccurException("WeddingHost does not contains this %s wedding in its Created Wedding Set".formatted(wedding.getWeddingTitle()));
//        }
//        this.createdWeddingSet.remove(wedding);
//        wedding.setWeddingHost(null); // Remove the back reference
//    }

}
