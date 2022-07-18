package ru.job4j.hibernate.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/scripts/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void deleteTable() throws SQLException {
        pool.getConnection().prepareStatement("DROP TABLE orders").executeUpdate();;
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrdersAndShowThemAll() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("one", "description1"));
        store.save(Order.of("two", "description2"));
        store.save(Order.of("three", "description3"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(3));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(1).getDescription(), is("description2"));
        assertThat(all.get(2).getDescription(), is("description3"));
    }

    @Test
    public void whenSaveOrdersAndFindById() {
        OrdersStore store = new OrdersStore(pool);
        Order one = Order.of("one", "description1");
        Order two = Order.of("two", "description2");
        Order tree = Order.of("three", "description3");
        store.save(one);
        store.save(two);
        store.save(tree);
        assertThat(store.findById(2), is(two));
    }

    @Test
    public void whenSaveOrdersAndUpdateIt() {
        OrdersStore store = new OrdersStore(pool);
        Order one = Order.of("one", "description1");
        Order two = Order.of("two", "description2");
        store.save(one);
        store.save(two);
        assertThat(store.update(two), is(two));
    }

    @Test
    public void whenSaveOrderAndGetByName() {
        OrdersStore store = new OrdersStore(pool);
        Order one = Order.of("one", "description");
        store.save(one);
        var listName = store.findByName("one");
        assertThat(listName.size(), is(1));
        assertThat(listName.get(0), is(one));
    }
}