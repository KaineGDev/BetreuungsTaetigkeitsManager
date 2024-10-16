package com.btm.btmanager;

import java.time.LocalDateTime;

public class Task {
    private int taetigkeit_id;
    private int m_id;
    private String beschreibung;
    private LocalDateTime startzeit;
    private LocalDateTime endzeit;

    public Task() {
    }

    public Task(String beschreibung, LocalDateTime startzeit, LocalDateTime endzeit) {
        this.beschreibung = beschreibung;
        this.startzeit = startzeit;
        this.endzeit = endzeit;
    }

    public Task(String description, LocalDateTime LocalDateTime, LocalDateTime LocalDateTime1, int id, int mid) {
        this.beschreibung = description;
        this.startzeit = LocalDateTime;
        this.endzeit = LocalDateTime1;
        this.taetigkeit_id = id;
        this.m_id = mid;
    }


    public int getTaetigkeit_id() {
        return taetigkeit_id;
    }

    public void setTaetigkeit_id(int taetigkeit_id) {
        this.taetigkeit_id = taetigkeit_id;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public LocalDateTime getStartzeit() {
        return startzeit;
    }

    public void setStartzeit(LocalDateTime startzeit) {
        this.startzeit = startzeit;
    }

    public LocalDateTime getEndzeit() {
        return endzeit;
    }

    public void setEndzeit(LocalDateTime endzeit) {
        this.endzeit = endzeit;
    }

    public int getMID() {
        return m_id;
    }
}
