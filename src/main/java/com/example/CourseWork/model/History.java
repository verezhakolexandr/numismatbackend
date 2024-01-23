package com.example.CourseWork.model;

import jakarta.persistence.*;

@Entity
@Table(name = "histories")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "history_body")
    private String historyBody;

    public History() {
    }

    public History(String historyBody) {
        this.historyBody = historyBody;
    }

    public void setHistoryBody(String historyBody) {
        this.historyBody = historyBody;
    }

    public Long getId() {
        return id;
    }

    public String getHistoryBody() {
        return historyBody;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", historyBody='" + historyBody + '\'' +
                '}';
    }
}
