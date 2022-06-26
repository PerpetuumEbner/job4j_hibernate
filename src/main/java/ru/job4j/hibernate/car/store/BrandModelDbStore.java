package ru.job4j.hibernate.car.store;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.job4j.hibernate.car.model.Brand;
import ru.job4j.hibernate.car.model.Model;

@ThreadSafe
@Repository
public class BrandModelDbStore {
    public void create(Session session) {
        Brand brand = Brand.of("Škoda");
        session.save(brand);

        Model one = Model.of("RAPID", brand);
        Model two = Model.of("OCTAVIA", brand);
        Model three = Model.of("KODIAQ", brand);
        Model fore = Model.of("SUPERB", brand);
        Model five = Model.of("KAROQ", brand);

        session.save(one);
        session.save(two);
        session.save(three);
        session.save(fore);
        session.save(five);
    }
}