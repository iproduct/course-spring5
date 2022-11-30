package course.spring.webmvc.util;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class TagsEditor extends PropertyEditorSupport {

        @Override
        public String getAsText() {
            Set<String> tags = (Set<String>) getValue();

            return tags == null ? "" : tags.stream().collect(Collectors.joining());
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            text = text.trim();
            if (text != null && text.length() > 0) {
                Set<String> tags = Arrays.stream(text.split("[,\s]+")).collect(Collectors.toSet());
                setValue(tags);
            } else {
                setValue(Set.of());
            }
        }
    }
