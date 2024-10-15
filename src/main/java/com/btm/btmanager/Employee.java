package com.btm.btmanager;

public class Employee {
    private int m_id;
    private String m_name;
    private String m_vorname;

    public Employee() {
    }

    public Employee(int m_id, String m_name, String m_vorname) {
        this.m_id = m_id;
        this.m_name = m_name;
        this.m_vorname = m_vorname;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_vorname() {
        return m_vorname;
    }

    public void setM_vorname(String m_vorname) {
        this.m_vorname = m_vorname;
    }
}
