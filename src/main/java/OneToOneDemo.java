import com.mysql.cj.xdevapi.AddResult;
import entity.Address;
import entity.Car;
import entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class OneToOneDemo {
    static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("java6");
    static EntityManager em = factory.createEntityManager();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Podaj nazwę użytkownika:");
        String name = scanner.nextLine();
        System.out.println("Podaj email:");
        String email = scanner.nextLine();

        Person person = new Person();
        person.setName(name);
        person.setEmail(email);

        System.out.println("Podaj dane adresu:");
        System.out.println("Ulica i nr domu:");
        String street = scanner.nextLine();
        System.out.println("miasto:");
        String city = scanner.nextLine();
        System.out.println("kod");
        String zipCode = scanner.nextLine();

        Address address = new Address(street, city, zipCode);
        person.setAddress(address);

        System.out.println("Podaj dane samochodu");
        System.out.println("nr rejestracyjny:");
        String registerNumber = scanner.nextLine();
        System.out.println("model:");
        String model = scanner.nextLine();

        Car car = new Car();
        car.setModel(model);
        car.setRegisterNumber(registerNumber);

        em.getTransaction().begin();
        //utrwalamy encję nadrzędną
        em.persist(car);
        //dodajemy encję nadrzędną do nieutrwalonej encji podrzędnej
        person.setCar(car);
        //utrwalamy encję podrzędną
        em.persist(person);
        em.getTransaction().commit();

        em.createQuery("from Person", Person.class)
                .getResultList().forEach(System.out::println);
        em.close();
    }
}
