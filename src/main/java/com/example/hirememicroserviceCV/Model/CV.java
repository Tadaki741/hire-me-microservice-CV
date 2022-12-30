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

    //    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL)
//    private List<User> users;
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String cvBody;
}
