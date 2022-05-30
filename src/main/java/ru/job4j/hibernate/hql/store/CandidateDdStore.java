package ru.job4j.hibernate.hql.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.hibernate.hql.model.Candidate;

import java.util.List;

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
     * @param candidate Новый кандидат.
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
        session.createQuery("update Candidate set name = :newName, experience = :newExperience, salary = :newSalary where id = :fId")
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
        session.createQuery("delete from Candidate where id = :fId")
                .setParameter("fId", id).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Поиск всех кандидатов в базе данных.
     *
     * @param sf SessionFactory.
     * @return Список найденных кандидатов.
     */
    public List findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        var result = session.createQuery("from Candidate").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Поиск кандидата по Id.
     *
     * @param id Id кандидата.
     * @param sf SessionFactory.
     * @return Найденный кандидат.
     */
    public Candidate findById(int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Candidate result = (Candidate) session.createQuery("from Candidate where id = :fId")
                .setParameter("fId", id).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Поиск кандидата по имени.
     *
     * @param name Имя кандидата.
     * @param sf   SessionFactory.
     * @return Список найденных имён.
     */
    public List findByName(String name, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        var result = session.createQuery("from Candidate where name = :name")
                .setParameter("name", name).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
