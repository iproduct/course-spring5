package course.springdata.mapping.init;

import course.springdata.mapping.dto.EmployeeDto;
import course.springdata.mapping.dto.ManagerDto;
import course.springdata.mapping.entity.Address;
import course.springdata.mapping.entity.Employee;
import course.springdata.mapping.service.AddressService;
import course.springdata.mapping.service.EmployeeService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppInitializer implements CommandLineRunner {
    private final EmployeeService employeeService;

    private final AddressService addressService;

    public AppInitializer(EmployeeService employeeService, AddressService addressService) {
        this.employeeService = employeeService;
        this.addressService = addressService;
    }

    @Override
    public void run(String... args) throws Exception {
        ModelMapper mapper = new ModelMapper();

        // 1. Create address and employee and map it to EmployeeDto
        Address address1 = new Address("Bulgaria", "Sofia", "Graf Ignatiev 50");
        address1 = addressService.addAddress(address1);
        Employee employee1 = new Employee("Ivan", "Petrov", 3500,
                LocalDate.of(1981, 5, 12), address1);
        employee1 = employeeService.addEmployee(employee1);

        EmployeeDto employeeDto = mapper.map(employee1, EmployeeDto.class);
        System.out.printf("EmployeeDto: %s%n", employeeDto);

        // 2. TipeMap mapping addresses and employees to ManagerDto with EmploeeDtos as subordinates
        List<Address> addresses = List.of(
                new Address("Bulgaria", "Sofia", "ul. G.S.Rakovski, 50"),
                new Address("Bulgaria", "Sofia", "Bul. Dondukov, 45"),
                new Address("Bulgaria", "Sofia", "ul. Hristo Smirnenski, 40"),
                new Address("Bulgaria", "Sofia", "bul. Alexander Malinov, 93a"),
                new Address("Bulgaria", "Sofia", "bul. Slivnitsa, 50"),
                new Address("Bulgaria", "Plovdiv", "ul. Angel Kanchev,34")
        );
        addresses = addresses.stream().map(addressService::addAddress).collect(Collectors.toList());

        List<Employee> employees = List.of(
                new Employee("Steve", "Adams", 5780, LocalDate.of(1982, 3, 12),
                        addresses.get(0)),
                new Employee("Stephen", "Petrov", 2760, LocalDate.of(1974, 5, 19),
                        addresses.get(1)),
                new Employee("Hristina", "Petrova", 3680, LocalDate.of(1991, 11, 9),
                        addresses.get(1)),
                new Employee("Diana", "Atanasova", 6790, LocalDate.of(1989, 12, 9),
                        addresses.get(2)),
                new Employee("Samuil", "Georgiev", 4780, LocalDate.of(1979, 2, 10),
                        addresses.get(3)),
                new Employee("Slavi", "Hristov", 3780, LocalDate.of(1985, 2, 23),
                        addresses.get(4)),
                new Employee("Georgi", "Miladinov", 3960, LocalDate.of(1995, 3, 11),
                        addresses.get(5))
        );
        List<Employee> created = employees.stream().map(employeeService::addEmployee).collect(Collectors.toList());

        // Add managers and update emplyees
        created.get(1).setManager(created.get(0));
        created.get(2).setManager(created.get(0));

        created.get(4).setManager(created.get(3));
        created.get(5).setManager(created.get(3));
        created.get(6).setManager(created.get(3));

        List<Employee> updated = created.stream().map(employeeService::updateEmployee).collect(Collectors.toList());

        // Fetch all managers and map them to ManagerDto
        TypeMap<Employee, ManagerDto> managerTypeMap = mapper.createTypeMap(Employee.class, ManagerDto.class)
                .addMappings(m -> {
                    m.map( Employee::getSubordinates, ManagerDto::setEmployees);
                    m.map( src -> src.getAddress().getCity(), ManagerDto::setCity);
//                    m.skip(ManagerDto::setCity);
                });
        mapper.getTypeMap(Employee.class, EmployeeDto.class).addMapping(
                src -> src.getAddress().getCity(), EmployeeDto::setCity
        );
        mapper.validate();

        List<Employee> managers = employeeService.getAllManagers();
        List<ManagerDto> managerDtos = managers.stream().map(managerTypeMap::map).collect(Collectors.toList());
        managerDtos.forEach(System.out::println);

        // 3. Employees born after 1990 with manager last name
        Converter<String, String> converterNoManager = ctx -> ctx.getSource() == null ? "[No manager]" : ctx.getSource();

        TypeMap employeeMap2 = mapper.getTypeMap(Employee.class, EmployeeDto.class)
                .addMappings(m -> m.using(converterNoManager)
                        .map(src -> src.getManager().getLastName(), EmployeeDto::setManagerLastName));



        System.out.println("-".repeat(80) + "\n");
        List<Employee> employeesBefore1990 = employeeService.getAllEmployeesBornBefore(LocalDate.of(1990, 1, 1));
//        employeesBefore1990.forEach(System.out::println);
        employeesBefore1990.stream().map(employeeMap2::map).forEach(System.out::println);
    }
}
