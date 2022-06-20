package ru.job4j.hibernate.car;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.car.model.Brand;
import ru.job4j.hibernate.car.model.Model;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            session.save(Model.of("RAPID"));
            session.save(Model.of("OCTAVIA"));
            session.save(Model.of("KODIAQ"));
            session.save(Model.of("SUPERB"));
            session.save(Model.of("KAROQ"));

            Brand brand = Brand.of("Škoda");
            for (int index = 1; index <= 5; index++) {
                brand.addModel(session.load(Model.class, index));
            }

            session.save(brand);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}