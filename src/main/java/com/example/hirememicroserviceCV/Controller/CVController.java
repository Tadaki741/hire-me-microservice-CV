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
    public ResponseEntity<ResponseBody<CV>> addCV(@RequestBody CVDTO cvdto) {
        CV cv = this.cvService.save(new CV(cvdto.getEmail(),cvdto.getName(),cvdto.getCvBody()));
        ResponseBody<CV> responseBody = new ResponseBody<>(cv);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping(path = "/test")
    public String test() {
        return "Test from CV controller";
    }


    @GetMapping
    public ResponseEntity<ResponseBody<List<CV>>> getAllCV(@RequestHeader(name = "Authorization") String requestHeader, @RequestParam(name = "email") String email) {
        logger.warning(" --> inside getAllCV function but is verifying data ! <--");

        try {
            String[] arrayData = requestHeader.split(" ");
            String extractedToken = arrayData[1];

            logger.warning("idToken is: " + extractedToken);

            boolean isCorrect = this.restService.verifyIDToken(extractedToken);

            if (isCorrect) {
                List<CV> userCVList = this.cvService.findAllCVWithEmail(email);
                ResponseBody<List<CV>> responseBodyAllCV = new ResponseBody<>(userCVList);
                return new ResponseEntity<>(responseBodyAllCV, HttpStatus.OK);
            }

            ResponseError responseError = new ResponseError("Token is not correct", HttpStatus.UNAUTHORIZED.value());
            ResponseBody responseBody = new ResponseBody<>(null, responseError);
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);

        } catch (NullPointerException exception) {
            ResponseError responseError = new ResponseError("Token not found", HttpStatus.BAD_REQUEST.value());
            ResponseBody responseBody = new ResponseBody<>(null, responseError);
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(path = "/{id}")
    public void updateCV(@PathVariable String id, @RequestParam String cvBody) throws Exception {
        cvService.updateCV(id, cvBody);
    }
}
