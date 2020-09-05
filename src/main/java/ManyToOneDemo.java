import entity.Order;
import entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class ManyToOneDemo {
    static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("java6");
    static EntityManager em = factory.createEntityManager();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Wybierz osobę składającą zamówienie");
        em.createQuery("from Person", Person.class)
                .getResultList().forEach(System.out::println);
        long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Podaj kwotę zamówienia:");
        int sum = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj numer:");
        String number = scanner.nextLine();
        Order order = new Order();
        order.setNumber(number);
        order.setSum(sum);
        //Pobieramy osobę z bazy
        Person person = em.find(Person.class, id);
        //Ustawiamy osobę zamawiającą
        order.setPerson(person);
        em.getTransaction().begin();
        //utrwalamy zamówienie
        em.persist(order);
        em.getTransaction().commit();
        em.createQuery("from Order", Order.class)
                .getResultList()
                .forEach(System.out::println);
        em.close();

        em.createQuery("select o from Order o where o.person = :person", Order.class)
                .setParameter("person", id)
                .getResultList()
                .forEach(System.out::println);
    }
}
