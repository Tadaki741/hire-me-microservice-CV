package com.example.hirememicroserviceCV.HttpResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class ResponseBody<T> {
    private final T data;
    private final ResponseError error;

    public ResponseBody(T data, ResponseError error) {
        this.data = data;
        this.error = error;
    }

    public ResponseBody(T data){
        this(data, null);
    }

    public ResponseBody(){
        this.data = null;
        this.error = null;
    }
}
