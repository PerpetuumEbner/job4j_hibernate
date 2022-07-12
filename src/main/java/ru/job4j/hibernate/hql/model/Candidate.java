package ru.job4j.hibernate.hql.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Модель описывающая кандидата.
 *
 * @author yustas
 * @version 1.0
 */
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String experience;

    private int salary;

    @OneToOne(fetch = FetchType.LAZY)
    private DbVacancy db;

    public static Candidate of(String name, String experience, int salary, DbVacancy db) {
        Candidate candidate = new Candidate();
        candidate.name = name;
        candidate.experience = experience;
        candidate.salary = salary;
        candidate.db = db;
        return candidate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public DbVacancy getDb() {
        return db;
    }

    public void setDb(DbVacancy db) {
        this.db = db;
    }

    @Override
    public String toString() {
        return String.format("Candidate: id=%s, name=%s, experience=%s, salary=%s, db=%s",
                id, name, experience, salary, db);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}