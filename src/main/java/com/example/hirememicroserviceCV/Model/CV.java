package com.example.hirememicroserviceCV.Model;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cv")
@TypeDefs({@TypeDef(name = "json", typeClass = JsonType.class)})
public class CV {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String cvBody;

    public CV(String email, String name, String cvBody) {
        this.email = email;
        this.name = name;
        this.cvBody = cvBody;
    }
}
