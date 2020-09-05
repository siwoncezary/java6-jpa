import entity.Alcohol;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class EntityDemo {
    static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("java6");
    static EntityManager em = factory.createEntityManager();
    static Scanner scanner = new Scanner(System.in);

    static int menu() {
        System.out.println("1. Dodaj encję");
        System.out.println("2. Wyświetl wszystkie encje");
        System.out.println("3. Edytuj encję");
        System.out.println("4. Usuń encję");
        System.out.println("5. Odłączanie encji");
        System.out.println("6. Scalanie encji");
        System.out.println("7. Znajdź encje wg nazwy");
        System.out.println("8. Liczba encji");
        System.out.println("0. Wyjście");
        while (!scanner.hasNextInt()) {
            System.out.println("Wpisz numer polecenia!!!");
        }
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    static void printAllEntities(){
        List<Alcohol> list = em.createQuery("from Alcohol", Alcohol.class).getResultList();
        list.forEach(System.out::println);
    }

    static void addEntity() {
        System.out.println("Podaj nazwę: ");
        String name = scanner.nextLine();
        System.out.println("Podaj kategorię: ");
        String category = scanner.nextLine();
        System.out.println("Podaj zawartość:");
        float voltage = scanner.nextFloat();
        System.out.println("Podaj pojemność:");
        float capacity = scanner.nextFloat();

        Alcohol beer = new Alcohol();
        beer.setCapacity(new BigDecimal(capacity));
        beer.setName(name);
        beer.setVoltage(new BigDecimal(voltage));
        beer.setCategory(category);

        em.getTransaction().begin();
        em.persist(beer);
        em.getTransaction().commit();
    }

    static void deleteEntity(){
        System.out.println("Podaj id usuwanej encji:");
        long id = scanner.nextLong();
        em.getTransaction().begin();
        Alcohol entity = em.find(Alcohol.class, id);
        em.remove(entity);
        em.getTransaction().commit();
    }

    static void editEntity(){
        System.out.println("Podaj id edytowanej encji:");
        long id = scanner.nextLong();
        scanner.nextLine();

        em.getTransaction().begin();
        //entity jest zarządzane przez managera i zmiany w obiekcie są utrwalane w bazie
        Alcohol entity = em.find(Alcohol.class, id);
        System.out.println(entity);
        System.out.println("Podaj nową nazwę:");
        String name = scanner.nextLine();
        System.out.println("Podaj nową pojemność:");
        float capacity = scanner.nextFloat();
        System.out.println("Podaj nową zawartość:");
        float voltage = scanner.nextFloat();
        entity.setName(name);
        entity.setVoltage(new BigDecimal(voltage));
        entity.setCapacity(new BigDecimal(capacity));
        //tu się kończy transakcja i zarządzanie obiektem przez managera
        em.getTransaction().commit();
    }

    static void detachEntity(){
        System.out.println("Podaj id odłączanej encji:");
        long id = scanner.nextLong();
        Alcohol entity = em.find(Alcohol.class, id);
        em.detach(entity);
        em.getTransaction().begin();
        entity.setName("");
        em.getTransaction().commit();
        System.out.println(entity);
    }

    static void mergeEntity(){
        System.out.println("Podaj id scalanej encji:");
        long id = scanner.nextLong();
        scanner.nextLine();
        Alcohol entity = new Alcohol();
        entity.setId(id);
        System.out.println("Podaj nazwę:");
        String name = scanner.nextLine();
        entity.setName(name);
        em.getTransaction().begin();
        Alcohol newEntity = em.merge(entity);
        em.getTransaction().commit();
        System.out.println(newEntity);
    }

    static void findByName(){
        System.out.println("Podaj szukaną nazwę");
        String nameTemplate = scanner.nextLine();
        List<Alcohol> list = em.createQuery("select a from Alcohol a where a.name like :template", Alcohol.class)
                .setParameter("template", nameTemplate)
                .getResultList();
        for(Alcohol a: list){
            System.out.println(a);
        }
    }

    static void countEntities(){
        long count = em.createNamedQuery("count", Long.class).getSingleResult();
        System.out.println("Liczba encji w bazie: " + count);
    }

    public static void main(String[] args) {
        while (true) {
            switch (menu()) {
                case 1:
                    addEntity();
                    break;
                case 2:
                    printAllEntities();
                    break;
                case 3:
                    editEntity();
                    break;
                case 4:
                    deleteEntity();
                    break;
                case 5:
                    detachEntity();
                    break;
                case 6:
                    mergeEntity();
                    break;
                case 7:
                    findByName();
                    break;
                case 8:
                    countEntities();
                    break;
                case 0:
                    return;
            }
        }
    }
}
