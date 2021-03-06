package ru.levelup.lesson09.repositiry;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.levelup.lesson09.domain.Country;

import java.util.List;

public class CountryRepositoryImpl implements CountryRepository {

    private final SessionFactory factory;


    public CountryRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Country create(String name, String capital, double population) {
        Session session = factory.openSession();
//        Transaction beginTransaction() начичинает транзакцию и возвращает объект Transaction.
        Transaction transaction = session.beginTransaction();

        Country country = new Country();
        country.setName(name);
        country.setCapital(capital);
        country.setPopulation(population);

        session.persist(country);
        transaction.commit();
        session.close();

        return country;
    }

    @Override
    public void delete(int id) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Country country = session.get(Country.class, id);
        session.delete(country);

        transaction.commit();
        session.close();
    }

    @Override
    public Country update(int id, String name, String capital, double population) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Country country = session.get(Country.class, id);
        country.setName(name);
        country.setCapital(capital);
        country.setPopulation(population);
        session.update(country);

        transaction.commit();
        session.close();
        return country;
    }

    @Override
    public Country getById(int id) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Country country = session.get(Country.class, id);

        transaction.commit();
        session.close();

        return country;
    }

    @Override
    public List<Country> getCountriesWithMillionPopulation() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Country> countries = session
                .createQuery("from Country where population > :population", Country.class )
                .setParameter("population", 1_000_000d)
                .getResultList();

        transaction.commit();
        session.close();
        return countries;
    }


}
