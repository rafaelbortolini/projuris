package br.com.projuris.os.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String description;
    private String date;
    @ManyToOne(fetch = FetchType.LAZY)
    private ServiceOrder serviceOrder;
}
