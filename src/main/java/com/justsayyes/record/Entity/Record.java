package com.justsayyes.record.Entity;

import com.justsayyes.record.DTO.RecordDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "record")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Record {
    @Id
    @Column(nullable = false)
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Visitor visitor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    @Column
    private Date createDate;

    public Record(RecordDTO recordDTO,Visitor v,Location l){
        this.visitor=v;
        this.location=l;
        if(!recordDTO.getTimestamp().equals("")){
            this.createDate=new Date(Long.parseLong(recordDTO.getTimestamp()));
        }else{
            this.createDate=new Date();
        }
    }
}
