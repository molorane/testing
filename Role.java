package org.dclm.theSeal.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tbl_role")
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String desc;

}
