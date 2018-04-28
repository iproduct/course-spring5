package org.iproduct.spring.xmlconfig;

import java.util.Properties;

public class Author {
    private String name;
    private int age;
    private Properties emails;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Properties getEmails() {
        return emails;
    }

    public void setEmails(Properties emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", emails=").append(emails);
        sb.append('}');
        return sb.toString();
    }
}
