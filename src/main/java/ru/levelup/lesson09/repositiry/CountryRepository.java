package ru.levelup.lesson09.repositiry;

import ru.levelup.lesson09.domain.Country;

public interface CountryRepository {

    Country create(String name, String capital, double population);


}