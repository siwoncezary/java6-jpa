import entity.Project;
import entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ManyToManyDemo {
    static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("java6");
    static EntityManager em = factory.createEntityManager();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Podaj nazwę projektu");
        String projectName = scanner.nextLine();
        System.out.println("Podaj id członków:");
        Set<Person> set = new HashSet<>();
        while(scanner.hasNextLong()){
            Person member = em.find(Person.class, scanner.nextLong());
            set.add(member);
        }
        Project project = new Project();
        project.setName(projectName);
        project.setMembers(set);

        em.getTransaction().begin();
        em.persist(project);
        em.getTransaction().commit();

        em.createQuery("from Project", Project.class)
                .getResultList()
                .forEach(System.out::println);

        Person person = em.find(Person.class, 1L);
        System.out.println(person);
    }
}
