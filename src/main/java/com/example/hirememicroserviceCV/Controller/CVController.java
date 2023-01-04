package com.example.hirememicroserviceCV.Controller;

import com.example.hirememicroserviceCV.HttpResponse.ResponseBody;
import com.example.hirememicroserviceCV.Model.CV;
import com.example.hirememicroserviceCV.Repository.CVRepository;
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

    @Autowired
    private RestService restService;

    private final CVService cvService;
    private final CVRepository cvRepository;

    public CVController(CVService cvService, CVRepository cvRepository) {
        this.cvService = cvService;
        this.cvRepository = cvRepository;
    }

    @PostMapping("/{email}/add")
    public void addCV(@PathVariable String email, @RequestBody String cvBody) {
//        if (userRepository.findByEmail(user.getEmail()) == null) userRepository.save(user);
        CV cv = new CV();
        cv.setCvBody(cvBody);
//        user.setCv(cv);
        cv.setEmail(email);
        cvRepository.save(cv);
    }

    @PostMapping(path = "/test")
    public String test() {
        return "Test from CV controller";
    }


    @GetMapping
    public ResponseEntity<ResponseBody> getAllCV(@RequestHeader(name = "Authorization") String requestHeader, @RequestParam(name = "email") String email) {


        logger.warning(" --> inside getAllCV function but is verifying data ! <--");

        try {
            String[] arrayData = requestHeader.split(" ");
            String extractedToken = arrayData[1];

            logger.warning("idToken is: " + extractedToken);

            boolean isCorrect = this.restService.verifyIDToken(extractedToken);

            if (isCorrect) {
                //TODO: query all the cv then return them
                List<CV> userCVList = this.cvService.findAllCVWithEmail(email);
                ResponseBody responseBodyAllCV = new ResponseBody(userCVList);
                return new ResponseEntity<>(responseBodyAllCV, HttpStatus.OK);
            }
        } catch (NullPointerException exception) {
            ResponseBody responseBody = new ResponseBody("Token not found !");
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }


        return null;
    }

}
