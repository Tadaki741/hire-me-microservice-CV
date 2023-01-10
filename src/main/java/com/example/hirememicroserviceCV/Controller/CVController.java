package com.example.hirememicroserviceCV.Controller;

import com.example.hirememicroserviceCV.HttpResponse.ResponseBody;
import com.example.hirememicroserviceCV.HttpResponse.ResponseError;
import com.example.hirememicroserviceCV.Model.CV;
import com.example.hirememicroserviceCV.Model.CVDTO;
import com.example.hirememicroserviceCV.Service.CVService;
import com.example.hirememicroserviceCV.Service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/cv")
@CrossOrigin(origins = "*")
public class CVController {

    private static final Logger logger = Logger.getLogger(CVController.class.getName());

    private final RestService restService;
    private final CVService cvService;

    @Autowired
    public CVController(CVService cvService, RestService restService) {
        this.cvService = cvService;
        this.restService = restService;
    }

    @PostMapping
    public ResponseEntity<ResponseBody<CV>> addCV(@RequestHeader(name = "Authorization") String requestHeader, @RequestBody CVDTO cvdto) {
        boolean isAuthenticated = this.restService.verifyIDToken(requestHeader);
        if (isAuthenticated) {
            CV cv = this.cvService.save(new CV(cvdto.getEmail(),cvdto.getName(),cvdto.getCvBody()));
            return new ResponseEntity<>(new ResponseBody<>(cv), HttpStatus.CREATED);
        } else {
            ResponseError responseError = new ResponseError("Unauthorized", HttpStatus.UNAUTHORIZED.value());
            ResponseBody responseBody = new ResponseBody<>(null, responseError);
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/test")
    public String test() {
        return "Test from CV controller";
    }


    @GetMapping
    public ResponseEntity<ResponseBody<List<CV>>> getAllCV(@RequestHeader(name = "Authorization") String requestHeader, @RequestParam(name = "email") String email) {
        logger.warning(" --> inside getAllCV function but is verifying data ! <--");

        try {
            boolean isAuthenticated = this.restService.verifyIDToken(requestHeader);

            if (isAuthenticated) {
                List<CV> userCVList = this.cvService.findAllCVWithEmail(email);
                ResponseBody<List<CV>> responseBodyAllCV = new ResponseBody<>(userCVList);
                return new ResponseEntity<>(responseBodyAllCV, HttpStatus.OK);
            }

            ResponseError responseError = new ResponseError("Token is not correct", HttpStatus.UNAUTHORIZED.value());
            ResponseBody responseBody = new ResponseBody<>(null, responseError);
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);

        } catch (NullPointerException exception) {
            ResponseError responseError = new ResponseError("Token not found", HttpStatus.UNAUTHORIZED.value());
            ResponseBody responseBody = new ResponseBody<>(null, responseError);
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        }

    }

    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity updateCV(@RequestHeader(name = "Authorization") String requestHeader, @PathVariable String id, @RequestBody CVDTO cvdto) throws Exception {
        boolean isAuthenticated = this.restService.verifyIDToken(requestHeader);
        if (!isAuthenticated) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        cvService.updateCV(id, cvdto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseBody<CV>> getCV(@RequestHeader(name = "Authorization") String requestHeader, @PathVariable String id) throws Exception {
        boolean isAuthenticated = this.restService.verifyIDToken(requestHeader);
        if (!isAuthenticated) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        CV cv = cvService.getCV(id);
        if (cv == null) {
            ResponseError responseError = new ResponseError("CV is not found", HttpStatus.NOT_FOUND.value());
            ResponseBody responseBody = new ResponseBody<>(null, responseError);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        ResponseBody<CV> responseBody = new ResponseBody<>(cv);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteCV(@RequestHeader(name = "Authorization") String requestHeader, @PathVariable String id){
        boolean isAuthenticated = this.restService.verifyIDToken(requestHeader);
        if (!isAuthenticated) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        this.cvService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
