package ru.job4j.hibernate.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.hql.model.Candidate;
import ru.job4j.hibernate.hql.model.DbVacancy;
import ru.job4j.hibernate.hql.model.Vacancy;

import java.time.Instant;
import java.util.Date;

public class HbmRun {
    public static void main(String[] args) {
        Candidate result = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Vacancy first = Vacancy.of("first", "firstDescription", Date.from(Instant.now()));
            Vacancy second = Vacancy.of("second", "secondDescription", Date.from(Instant.now()));
            Vacancy fourth = Vacancy.of("fourth", "fourthDescription", Date.from(Instant.now()));
            session.save(first);
            session.save(second);
            session.save(fourth);

            DbVacancy db = DbVacancy.of("db");
            db.addVacancy(first);
            db.addVacancy(second);
            db.addVacancy(fourth);
            session.save(db);

            Candidate one = Candidate.of("Alex", "3 year", 2500, db);
            Candidate two = Candidate.of("Nikolay", "6 year", 7000, db);
            Candidate three = Candidate.of("Nikita", "7 year", 8500, db);
            session.save(one);
            session.save(two);
            session.save(three);

            result = session.createQuery(
                    "select distinct c from Candidate c join fetch c.db d join fetch d.vacancies v where c.id = :id",
                    Candidate.class).setParameter("id", 1).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(result);
    }
}