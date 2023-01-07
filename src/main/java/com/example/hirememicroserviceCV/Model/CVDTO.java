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

    @JsonCreator
    public CVDTO(String name, String cvBody) {
        this.name = name;
        this.cvBody = cvBody;
    }


}
