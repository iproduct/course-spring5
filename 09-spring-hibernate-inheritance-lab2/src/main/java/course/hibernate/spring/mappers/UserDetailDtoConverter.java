package course.hibernate.spring.mappers;

import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import course.hibernate.spring.dto.UserDetailDto;
import course.hibernate.spring.entity.User;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

//@Component
public class UserDetailDtoConverter extends ConverterConfigurerSupport<User, UserDetailDto> {

    @Override
    protected Converter<User, UserDetailDto> converter() {
        return new AbstractConverter<User, UserDetailDto>() {

            @Override
            protected UserDetailDto convert(User source) {
                UserDetailDto result = new UserDetailDto();
                BeanUtils.copyProperties(source, result);
//                UserDetailDto author = new UserDetailDto();
//                BeanUtils.copyProperties(source.getAuthor(), author);
//                result.setAuthor(author);
//                result.setRoles(source.getRoles());
                return result;
            }
        };
    }
}
