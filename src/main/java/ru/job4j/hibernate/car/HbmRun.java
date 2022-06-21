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

            Model one = Model.of("RAPID");
            Model two = Model.of("OCTAVIA");
            Model three = Model.of("KODIAQ");
            Model fore = Model.of("SUPERB");
            Model five = Model.of("KAROQ");

            session.save(one);
            session.save(two);
            session.save(three);
            session.save(fore);
            session.save(five);

            Brand brand = Brand.of("Škoda");
            brand.addModel(session.load(Model.class, 1));
            brand.addModel(session.load(Model.class, 2));
            brand.addModel(session.load(Model.class, 3));
            brand.addModel(session.load(Model.class, 4));
            brand.addModel(session.load(Model.class, 5));

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