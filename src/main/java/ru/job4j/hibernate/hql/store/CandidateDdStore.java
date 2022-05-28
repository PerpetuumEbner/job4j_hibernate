package ru.job4j.hibernate.hql.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.hibernate.hql.model.Candidate;

/**
 * В классе происходит обработка кандидатов в базе данных.
 *
 * @author yustas
 * @version 1.0
 */
public class CandidateDdStore {
    /**
     * Добавление кандидата в базу данных.
     *
     * @param candidate Кандидат.
     * @param sf        SessionFactory.
     */
    public void create(Candidate candidate, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(candidate);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Обновление параметров кандидата по id.
     *
     * @param id         Id кандидата которого нужно обновить.
     * @param name       Новое имя кандидата.
     * @param experience Новый опыт кандидата.
     * @param salary     Новая зарплата кандидата.
     * @param sf         SessionFactory.
     */
    public void update(int id, String name, String experience, int salary, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                        "update Candidate set name = :newName, experience = :newExperience, salary = :newSalary where id = :fId")
                .setParameter("fId", id)
                .setParameter("newName", name)
                .setParameter("newExperience", experience)
                .setParameter("newSalary", salary)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Удаление кандидата по id.
     *
     * @param id Id кандидата.
     * @param sf SessionFactory.
     */
    public void delete(int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                        "delete from Candidate where id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Поиск всех кандидатов в базе данных.
     *
     * @param sf SessionFactory.
     */
    public void findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("from Candidate").list();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Поиск кандидата по Id.
     *
     * @param id Id кандидата.
     * @param sf SessionFactory.
     */
    public void findById(int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("from Candidate where id = :fId").setParameter("fId", id).uniqueResult();
        session.getTransaction().commit();
        session.close();
    }
}
