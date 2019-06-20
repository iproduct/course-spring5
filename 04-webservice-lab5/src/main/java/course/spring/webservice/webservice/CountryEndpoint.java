package course.spring.webservice.webservice;

import course.spring.webservice.dao.CountryRepository;
import course.spring.webservice.wsdl.GetCountryRequest;
import course.spring.webservice.wsdl.GetCountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.addressing.server.annotation.Action;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;
import org.springframework.ws.soap.server.endpoint.annotation.SoapActions;

@Endpoint
public class CountryEndpoint {
    public static final String NAMESPACE_URI = "http://iproduct.org/course/spring-web-service";

    @Autowired
    private CountryRepository repo;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @SoapAction("http://iproduct.org/course/spring-web-service/GetCountryRequest")
//    @Action(value = "getCountryRequest", output="getCountryResponse")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest req) {
        GetCountryResponse resp = new GetCountryResponse();
        resp.setCountry(repo.findCountry(req.getName()));
        return resp;
    }
}
