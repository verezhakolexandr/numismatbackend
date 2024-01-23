package com.example.CourseWork.model;

import jakarta.persistence.*;

@Entity
@Table(name = "coins")
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "history_id", referencedColumnName = "id")
    private History history;

    public Coin() {
    }

    public Coin(Country country, History history) {
        this.country = country;
        this.history = history;
    }

    public Long getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public History getHistory() {
        return history;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "id=" + id +
                ", country=" + country +
                ", history=" + history +
                '}';
    }
}
