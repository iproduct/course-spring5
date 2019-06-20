package course.spring.webservice.config;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import org.springframework.stereotype.Component;

public class PrefixMapper extends NamespacePrefixMapper {

    private static final String FOO_PREFIX = "cs"; // DEFAULT NAMESPACE
    private static final String FOO_URI = "http://iproduct.org/course/spring-web-service";


    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if (FOO_URI.equals(namespaceUri)) {
            return FOO_PREFIX;
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[]{FOO_URI};
    }

}
