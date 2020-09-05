import entity.Address;
import entity.Car;
import entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class CascadeDemo {
    static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("java6");
    static EntityManager em = factory.createEntityManager();

    public static void main(String[] args) {
        Person person = new Person();
        person.setEmail("email");
        person.setName("alina");
        Address address = new Address("Prosta 5", "Toruń", "45-980");
        person.setAddress(address);

        Car car = new Car();
        car.setRegisterNumber("TR-56779");
        car.setModel("FIAT 500");

        em.getTransaction().begin();
        em.persist(car);
        person.setCar(car);

        em.persist(person);
        em.createQuery("from Person", Person.class)
                .getResultList().forEach(System.out::println);
        Scanner scanner = new Scanner(System.in);
        em.getTransaction().commit();

        scanner.nextLine();

        em.getTransaction().begin();
        //usunięcie właściela spwoduje automatyczne usunięcie jego samochodu i wynika to z kaskady
        em.remove(person);
        System.out.println("PO USUNIĘCIU");
        em.createQuery("from Person", Person.class)
                .getResultList().forEach(System.out::println);
        em.getTransaction().commit();

        em.close();
    }
}
