package com.baipiao.api.categories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baipiao.api.events.Event;
import com.baipiao.api.tickets.Ticket;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

@Data
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // To match SQL SERIAL
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)  // Matches SQL definition of VARCHAR(50)
    private String name;

    private String description;

    @ToString.Exclude  // This will exclude the event reference from the toString() method
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    // Constructor for creating a new category
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}