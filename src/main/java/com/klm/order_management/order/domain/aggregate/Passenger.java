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
}
