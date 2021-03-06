package com.justsayyes.record.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "visitor")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Visitor {
    @Id
    @Column(nullable = false)
    String email;

    @OneToMany(mappedBy = "visitor",cascade = CascadeType.ALL)
    private List<Record> records=new ArrayList<>();

    @OneToMany(mappedBy = "visitor",cascade = CascadeType.ALL)
    private List<Status> statuses=new ArrayList<>();
}
