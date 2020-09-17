package br.com.projuris.os.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String start;
    private String finish;

    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private String customerPhone;

    private String brand;
    private String type;
    private String issue;
    private String status;

    private String workerName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> history = new ArrayList<>();
}
