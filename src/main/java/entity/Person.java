package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String name;

    @Embedded
    private Address address;

    //ta kaskada powoduje automatyczne usunięcie samochodu, gdy zostanie usunięta właściciel
    @OneToOne(cascade = CascadeType.REMOVE)
    private Car car;

    //Dodanie drugiego kierunku do zwiazku Person-Order
    //Nie musimy teraz robić zapytania listującego zamówienia tego użytkownika
    //pobieranie zamówień wykonywane jest od razu przy pobieraniu z bazy encji person
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private Set<Order> orders = new HashSet<>();

    //pobieranie nastapi gdy zostanie wywołana metoda getMembers()
    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", car=" + car +
                ", orders=" + orders +
                ", projects=" + projects +
                '}';
    }
}
