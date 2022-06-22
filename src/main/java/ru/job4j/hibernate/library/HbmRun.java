package ru.job4j.hibernate.library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.library.model.Author;
import ru.job4j.hibernate.library.model.Book;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book one = Book.of("Война и мир");
            Book two = Book.of("Мастер и Маргарита");
            Book three = Book.of("Преступление и наказание");
            Book four = Book.of("Анна Каренина");
            Book five = Book.of("Идиот");
            Book six = Book.of("Собачье сердце");

            Author first = Author.of("Лев Толстой");
            first.getBooks().add(one);
            first.getBooks().add(four);
            Author second = Author.of("Федор Достоевский");
            second.getBooks().add(three);
            second.getBooks().add(five);
            Author fourth = Author.of("Михаил Булгаков");
            fourth.getBooks().add(two);
            fourth.getBooks().add(six);

            session.persist(first);
            session.persist(first);
            session.persist(second);
            session.persist(fourth);

            Author author = session.get(Author.class, 1);
            session.remove(author);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}