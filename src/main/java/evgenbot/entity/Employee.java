package evgenbot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "salary")
    private double salary;
    @Column(name = "department")
    private String department;
    @Column(name = "status")
    private String status;
    public Employee(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee(String name,String status) {
        this.name = name;
        this.status = status;
    }

    public Employee(String name, String surname, double salary, String department, String status) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.department = department;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Работник [" + id + "]"+
                "\nName = " + name +
                "\nSurname = " + surname +
                "\nSalary = " + salary +
                "\nDepartment = " + department;
    }
}
