package course.hibernate.spring.dto.mapping;
import course.hibernate.spring.dto.UserCreateDto;
import course.hibernate.spring.dto.UserDetailDto;
import course.hibernate.spring.dto.UserSummaryDto;
import course.hibernate.spring.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public static User mapUserDtoToUser(UserCreateDto source) {
        User result = new User();
        BeanUtils.copyProperties(source, result);
        return result;
    }

    public static User mapUserSummaryDtoToUser(UserSummaryDto source) {
        User result = new User();
        BeanUtils.copyProperties(source, result);
        return result;
    }

    public static UserSummaryDto mapUserToUserSummaryDto(User source) {
        UserSummaryDto result = new UserSummaryDto();
        BeanUtils.copyProperties(source, result);
        return result;
    }
    public static UserDetailDto mapUserToUserDetailDto(User source) {
        var result = new UserDetailDto();
        BeanUtils.copyProperties(source, result);
        return result;
    }
}
