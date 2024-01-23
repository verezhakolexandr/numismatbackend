package com.example.CourseWork.model;

import jakarta.persistence.*;

@Entity
@Table(name = "collectors")
public class Collector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "number_of_coins")
    private Long numberOfCoins;
    @Column(name = "number_of_rare_coins")
    private Long numberOfRareCoins;

    public Collector() {
    }

    public Collector(String name, String surname, Long numberOfCoins, Long numberOfRareCoins) {
        this.name = name;
        this.surname = surname;
        this.numberOfCoins = numberOfCoins;
        this.numberOfRareCoins = numberOfRareCoins;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNumberOfCoins(Long numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public void setNumberOfRareCoins(Long numberOfRareCoins) {
        this.numberOfRareCoins = numberOfRareCoins;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Long getNumberOfCoins() {
        return numberOfCoins;
    }

    public Long getNumberOfRareCoins() {
        return numberOfRareCoins;
    }

    @Override
    public String toString() {
        return "Collector{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", numberOfCoins=" + numberOfCoins +
                ", numberOfRareCoins=" + numberOfRareCoins +
                '}';
    }
}
