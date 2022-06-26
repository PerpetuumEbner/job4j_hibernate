package ru.job4j.hibernate.car;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.car.model.Brand;
import ru.job4j.hibernate.car.model.Model;
import ru.job4j.hibernate.car.store.BrandModelDbStore;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            new BrandModelDbStore().create(session);
            print(session);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void print(Session session) {
        List<Brand> list;
        list = session.createQuery("from Brand").list();
        for (Brand brand : list) {
            for (Model model : brand.getModels()) {
                System.out.println(model);
            }
        }
    }
}