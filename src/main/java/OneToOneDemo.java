import com.mysql.cj.xdevapi.AddResult;
import entity.Address;
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

        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        em.createQuery("from Person", Person.class)
                .getResultList().forEach(System.out::println);
        em.close();
    }
}
