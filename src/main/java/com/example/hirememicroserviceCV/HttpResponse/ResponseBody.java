package com.example.hirememicroserviceCV.HttpResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class ResponseBody {
    private final Object data;
    private final ResponseBody error;

    public ResponseBody(Object data, ResponseBody error) {
        this.data = data;
        this.error = error;
    }

    public ResponseBody(Object data){
        this(data, null);
    }

    public ResponseBody(){
        this.data = null;
        this.error = null;
    }
}
