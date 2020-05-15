package flower.rest.server.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEE")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employee_id;

    @Column(name = "employee_name")
    private String employee_name;

    @Column(name = "employee_telephone")
    private int employee_telephone;


}
