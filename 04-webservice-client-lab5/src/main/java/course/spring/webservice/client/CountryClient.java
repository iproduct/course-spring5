package course.spring.webservice.client;

import course.spring.webservice.wsdl.GetCountryRequest;
import course.spring.webservice.wsdl.GetCountryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Slf4j
public class CountryClient extends WebServiceGatewaySupport {
    public static final String ENDPOINT_URI = "http://localhost:8080/ws";
    public GetCountryResponse getCountry(String country) {
        GetCountryRequest req = new GetCountryRequest();
        req.setName(country);
        log.info("Requesting location for {}", country);

        GetCountryResponse resp = (GetCountryResponse) getWebServiceTemplate().marshalSendAndReceive(ENDPOINT_URI, req,
                new SoapActionCallback(
                        "http://iproduct.org/course/spring-web-service/GetCountryRequest"));
        return resp;
    }
}
