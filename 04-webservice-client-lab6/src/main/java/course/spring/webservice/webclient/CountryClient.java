package course.spring.webservice.webclient;

import course.spring.webservice.wsdl.GetCountryRequest;
import course.spring.webservice.wsdl.GetCountryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Slf4j
public class CountryClient extends WebServiceGatewaySupport {
    public static final String WEB_SERVICE_URI = "http://localhost:8080/ws";
    public static final String SOAP_ACTION = "http://iproduct.org/course/spring-web-service/GetCountryRequest";

    public GetCountryResponse getCountry(String country) {
        GetCountryRequest request = new GetCountryRequest();
        request.setName(country);
        log.info("Requsting information for country: {}", country);

        GetCountryResponse response = (GetCountryResponse) getWebServiceTemplate()
                .marshalSendAndReceive(WEB_SERVICE_URI, request,
                        new SoapActionCallback(SOAP_ACTION));
        return response;
    }
}
