package com.klm.order_management.order.domain.aggregate;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String nationality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    protected Passenger() {
    }

    public Passenger(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    void setOrder(Order order) {
        this.order = order;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}

