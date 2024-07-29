package evgenbot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client {
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "state")
    private String state;

    @OneToOne()
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Client(String name, String surname, long id, String state) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.state = state;
    }
    public Client(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
