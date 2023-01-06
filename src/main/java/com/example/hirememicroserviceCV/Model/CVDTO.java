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

    @JsonProperty("CVname")
    private String CVname;

    @Type(type = "json")
    @JsonProperty("CVBody")
    private String CVBody;

    @JsonProperty("email")
    private String email;

    @JsonCreator
    public CVDTO(String CVname, String CVBody, String email) {
        this.CVname = CVname;
        this.CVBody = CVBody;
        this.email = email;
    }


}
