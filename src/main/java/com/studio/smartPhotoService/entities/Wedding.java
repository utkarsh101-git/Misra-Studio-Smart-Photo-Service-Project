package com.studio.smartPhotoService.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wedding {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long weddingId;

    // This code is created internally & most crucial code which should be shared among members & host
    private String weddingUniqueCode;

    @NotEmpty
    private String weddingTitle;

    @NotNull
    private LocalDateTime weddingDateTime;

    @ElementCollection
    @CollectionTable(name = "wedding_event_dates", joinColumns = @JoinColumn(name = "wedding_event_id"))
    @MapKeyColumn(name = "event_name")
    @Column(name = "event_date")
    @NotNull
    private Map<String, LocalDateTime> weddingEventDates;

    // wedding host cannot be null because wedding object is created by Wedding Host only
    @ManyToOne
    @JsonBackReference("WeddingHostWeddingObjects")
    private WeddingHost weddingHost;

    // wedding can have multiple wedding Members connected to it
    // may need Managed and back reference in future
    @ManyToMany(mappedBy = "attendsWeddingSet")
    private Set<WeddingMember> weddingMembers = new HashSet<>();

    /**
     * Helper method to link {@link WeddingMember} and {@link Wedding}, helps in syncing
     *
     * @param weddingMember
     */
    public void addWeddingMember(WeddingMember weddingMember) {
        weddingMembers.add(weddingMember);
        weddingMember.getAttendsWeddingSet().add(this); // Maintain synchronization
    }

    /**
     * Helper method to de-link {@link WeddingMember} and {@link Wedding}, helps in syncing
     *
     * @param weddingMember
     */
    public void removeWeddingMember(WeddingMember weddingMember) {
        weddingMembers.remove(weddingMember);
        weddingMember.getAttendsWeddingSet().remove(this); // Maintain synchronization
    }
}
