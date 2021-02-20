package com.bdcourse.library.edition;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "edition")
public class edition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_edition")
    @SequenceGenerator(name = "seq_gen_edition", sequenceName = "seq_edition")
    @Column(name = "edition_id")
    private Integer edition_id;

    @Column(name = "usage_type")
    private String usage_type;

    @Column(name = "position_id")
    private Integer position_id;

    @Column(name = "publication_id")
    private Integer publication_id;

    public Integer getEdition_id() {
        return edition_id;
    }

    public void setEdition_id(Integer edition_id) {
        this.edition_id = edition_id;
    }

    public String getUsage_type() {
        return usage_type;
    }

    public void setUsage_type(String usage_type) {
        this.usage_type = usage_type;
    }

    public Integer getPosition_id() {
        return position_id;
    }

    public void setPosition_id(Integer position_id) {
        this.position_id = position_id;
    }

    public Integer getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(Integer publication_id) {
        this.publication_id = publication_id;
    }
}
