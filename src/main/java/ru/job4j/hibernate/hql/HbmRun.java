package ru.job4j.hibernate.hql;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.hql.model.Candidate;
import ru.job4j.hibernate.hql.store.CandidateDdStore;

public class HbmRun {
    public static void main(String[] args) {
        CandidateDdStore store = new CandidateDdStore();

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            store.create(Candidate.of("Alex", "3 year", 2500), sf);
            store.create(Candidate.of("Nikolay", "6 year", 7000), sf);
            store.create(Candidate.of("Nikita", "7 year", 8500), sf);
            for (Object candidate : store.findAll(sf)) {
                System.out.println(candidate);
            }
            store.delete(1, sf);
            store.update(3, "Nikita", "9 year", 10500, sf);
            System.out.println(store.findById(3, sf));
            for (Object candidate : store.findByName("Nikolay", sf)) {
                System.out.println(candidate);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}