package com.btm.btmanager;

import java.time.LocalTime;

public class Task {
    private int taetigkeit_id;
    private int m_id;
    private String beschreibung;
    private LocalTime startzeit;
    private LocalTime endzeit;

    public Task() {
    }

    public Task(String beschreibung, LocalTime startzeit, LocalTime endzeit) {
        this.beschreibung = beschreibung;
        this.startzeit = startzeit;
        this.endzeit = endzeit;
    }

    public Task(String description, LocalTime localTime, LocalTime localTime1, int id, int mid) {
        this.beschreibung = description;
        this.startzeit = localTime;
        this.endzeit = localTime1;
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

    public LocalTime getStartzeit() {
        return startzeit;
    }

    public void setStartzeit(LocalTime startzeit) {
        this.startzeit = startzeit;
    }

    public LocalTime getEndzeit() {
        return endzeit;
    }

    public void setEndzeit(LocalTime endzeit) {
        this.endzeit = endzeit;
    }

    public int getMID() {
        return m_id;
    }
}
