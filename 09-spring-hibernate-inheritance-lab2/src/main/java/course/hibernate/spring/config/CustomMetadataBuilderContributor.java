package course.hibernate.spring.config;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

@Component
public class CustomMetadataBuilderContributor implements MetadataBuilderContributor {

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
//        metadataBuilder.applyPhysicalNamingStrategy(new MyOrgPhysicalNamingStrategy());
        metadataBuilder.applySqlFunction("config_json_extract",
                new StandardSQLFunction("json_extract", StandardBasicTypes.STRING));
        metadataBuilder.applySqlFunction("JSON_UNQUOTE",
                new StandardSQLFunction("JSON_UNQUOTE", StandardBasicTypes.STRING));
        metadataBuilder.applySqlFunction("group_concat",
                new StandardSQLFunction("GROUP_CONCAT", StandardBasicTypes.STRING));

    }

}
