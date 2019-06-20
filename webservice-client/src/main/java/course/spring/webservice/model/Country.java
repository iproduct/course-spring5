package course.spring.webservice.model;

import course.spring.webservice.wsdl.Currency;
import lombok.Data;

@Data
public class Country {
    protected String name;
    protected int population;
    protected String capital;
    protected Currency currency;

    public Country(course.spring.webservice.wsdl.Country source) {
        this.name = source.getName();
        this.population = source.getPopulation();
        this.capital = source.getCapital();
        this.currency = source.getCurrency();
    }
}
