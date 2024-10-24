package com.studio.smartPhotoService.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WeddingMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long weddingMemberId;

    @NotEmpty
    private String weddingMemberName;
    @NotEmpty
    private String weddingMemberEmailId;

    // Member Relation can be empty of null also
    private String weddingMemberRelation;

    // can be null initially null or empty
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "attends_wedding", // Name of the join table
            joinColumns = @JoinColumn(name = "wedding_member_name", referencedColumnName = "weddingMemberName"), // Join column for weddingMemberName
            inverseJoinColumns = @JoinColumn(name = "wedding_id", referencedColumnName = "weddingId") // Join column for weddingId
    )
    /// may need Managed and back reference in future
    private Set<Wedding> attendsWeddingSet = new HashSet<>();

    // can be null initially , it may have some values after photos are clicked
    @ElementCollection
    private List<String> weddingMemberImagePathList;

}
