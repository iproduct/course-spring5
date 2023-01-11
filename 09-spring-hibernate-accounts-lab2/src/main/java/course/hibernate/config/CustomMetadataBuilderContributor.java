package course.hibernate.config;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

@Component
public class CustomMetadataBuilderContributor implements MetadataBuilderContributor {

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder
                .applySqlFunction("config_json_extract",
                        new StandardSQLFunction("json_extract", StandardBasicTypes.STRING))
                .applySqlFunction("JSON_UNQUOTE",
                        new StandardSQLFunction("JSON_UNQUOTE", StandardBasicTypes.STRING))
                .applySqlFunction("group_concat",
                        new StandardSQLFunction("group_concat", StandardBasicTypes.STRING))
                .applySqlFunction("group_concat_role_set",
                        new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));

    }

    private PhysicalNamingStrategy getPhysicalNamingStrategy() {
        return new MyOrgPhysicalNamingStrategy();
    }

}
