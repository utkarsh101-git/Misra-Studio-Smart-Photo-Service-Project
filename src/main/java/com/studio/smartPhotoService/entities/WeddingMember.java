package com.studio.smartPhotoService.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
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
    private List<String> weddingMemberImagePathList = new ArrayList<>();

    /**
     * Helper method to link {@link WeddingMember} and {@link Wedding}, helps in syncing
     *
     * @param wedding
     */
    public void addWedding(Wedding wedding) {
        attendsWeddingSet.add(wedding);
        wedding.getWeddingMembers().add(this); // Maintain synchronization
    }

    /**
     * Helper method to de-link {@link WeddingMember} and {@link Wedding}, helps in syncing
     *
     * @param wedding
     */
    public void removeWedding(Wedding wedding) {
        attendsWeddingSet.remove(wedding);
        wedding.getWeddingMembers().remove(this); // Maintain synchronization
    }

}
