package ru.levelup.lesson09.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import ru.levelup.lesson09.domain.Country;
import ru.levelup.lesson09.repositiry.CountryRepository;
import ru.levelup.lesson09.repositiry.CountryRepositoryImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CountryRepositoryTest {

    //Dummy
    //Stub
    //Mock

    private CountryRepository repository;

    private SessionFactory factory;
    private Session session;
    private Transaction transaction;

    @Before
    public void setup(){
        factory = mock(SessionFactory.class);
//        System.out.println(factory.getClass().getName());
        session = mock(Session.class);
        transaction = mock(Transaction.class);
//        System.out.println(factory.openSession());
//        System.out.println(session);
        repository  = new CountryRepositoryImpl(factory);

        when(factory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void testCreate_validData_returnSavedObject(){
        String name = "Russia";
        String capital = "Moscow";
        double population = 147_000_000d;

        Country country = repository.create(name, capital, population);
        assertEquals(name, country.getName());
        assertEquals(capital, country.getCapital());
        assertEquals(population, country.getPopulation(), 0.2d);
//      обертка Matchers
        verify(session, times(1)).persist(Matchers.any(Country.class));
        verify(transaction).commit();
        verify(session).close();

    }

    @Test
    public void testDelete_Id(){
        //Методы do*() применяются для создания заглушек на void методы.
        //ожидаем true когда repository удалит int
        when(session.get(Country.class, 1)).thenReturn(new Country());
        repository.delete(1);

        //Проверяем что session был вызван и у него был вызван get с параметрами
        verify(session).delete(Matchers.any(Country.class));

        //Если все норм, то  тейс проиден

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void teatUpdate(){
        String name = "Russia2";
        String capital = "Moscow2";
        double population = 1000_000_000;

        when(session.get(Country.class, 1)).thenReturn(new Country());

        Country country = repository.update(1, name, capital,population);
        assertEquals(name, country.getName());
        assertEquals(capital, country.getCapital());
        assertEquals(population, country.getPopulation(), 0.2d);

        verify(session).update(Matchers.any(Country.class));

        verify(transaction).commit();
        verify(session).close();


    }

}
