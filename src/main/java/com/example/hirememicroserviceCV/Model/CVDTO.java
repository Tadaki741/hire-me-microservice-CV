package com.example.hirememicroserviceCV.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

@Getter
@Setter
@ToString
public class CVDTO {

    @JsonProperty("name")
    private String name;

    @Type(type = "json")
    @JsonProperty("cvBody")
    private String cvBody;

    @JsonProperty("email")
    private String email;

    @JsonCreator
    public CVDTO(String name, String cvBody, String email) {
        this.name = name;
        this.cvBody = cvBody;
        this.email = email;
    }


}
