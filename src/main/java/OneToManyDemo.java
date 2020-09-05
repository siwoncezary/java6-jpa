import entity.Order;
import entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class OneToManyDemo {
    static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("java6");
    static EntityManager em = factory.createEntityManager();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Podaj id użytkowanika:");
        long id = scanner.nextLong();
        Person person = em.find(Person.class, id);
        System.out.println("Zamówienia użytkownika " + person.getName());
        for(Order o: person.getOrders()){
            System.out.println(o);
        }
        System.out.println(person);
    }
}
