package com.baipiao.api.organizations;

import java.io.Serializable;
import java.util.List;

import com.baipiao.api.events.Event;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "organizations")
@ToString(exclude = "events")
public class Organization implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // To match SQL SERIAL behavior
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)  // Matches SQL VARCHAR(100)
    private String name;

    private String description;

    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private String phoneno;


    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Basic(fetch = FetchType.EAGER)
    private List<Event> events;

    // Constructor
    public Organization(String name, String description, String email, String phoneno) {
        this.name = name;
        this.description = description;
        this.email = email;
        this.phoneno = phoneno;
    }
}