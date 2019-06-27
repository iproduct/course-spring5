package course.spring.webservice.model;

import course.spring.webservice.wsdl.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private String name;
    private int population;
    private String capital;
    private Currency currency;

    public Country(course.spring.webservice.wsdl.Country country) {
        name = country.getName();
        population = country.getPopulation();
        capital = country.getCapital();
        currency = country.getCurrency();
    }
}
