package course.spring.webservice.web;

import course.spring.webservice.dao.CountryRepository;
import course.spring.webservice.wsdl.GetCountryRequest;
import course.spring.webservice.wsdl.GetCountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

@Endpoint
public class CountryEndpoint {
    public static final String NAMESPACE_URI =
            "http://iproduct.org/course/spring-web-service";

    @Autowired
    private CountryRepository countryRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @SoapAction("http://iproduct.org/course/spring-web-service/GetCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));
        return response;
    }
}
