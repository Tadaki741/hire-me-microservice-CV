package com.example.hirememicroserviceCV.Controller;

import com.example.hirememicroserviceCV.HttpResponse.ResponseBody;
import com.example.hirememicroserviceCV.Model.CV;
import com.example.hirememicroserviceCV.Model.LoginBody;
import com.example.hirememicroserviceCV.Repository.CVRepository;
import com.example.hirememicroserviceCV.Service.CVService;
import com.example.hirememicroserviceCV.Service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{id}/add")
    public void addCV(@PathVariable String id, @RequestBody String cvBody) {
//        if (userRepository.findByEmail(user.getEmail()) == null) userRepository.save(user);
        CV cv = new CV();
        cv.setCvBody(cvBody);
//        user.setCv(cv);
        cv.setUserId(id);
        cvRepository.save(cv);
    }

    @PostMapping(path = "/test")
    public String test () {
        return "Test from CV controller";
    }


    @PostMapping(path = "/getAllCv")
    public ResponseEntity<ResponseBody> getAllCV (@RequestBody LoginBody loginBody){
        logger.warning(" --> inside getAllCV function but is verifying data ! <--");
        logger.warning("idToken is: " + loginBody.getIdToken());

        boolean isCorrect = this.restService.verifyIDToken(loginBody);
        ResponseBody responseBody = new ResponseBody(isCorrect);

        if (isCorrect){
            //TODO: query all the cv then return them
            


            return new ResponseEntity<>(responseBody,HttpStatus.OK);
        }

        return new ResponseEntity<>(responseBody,HttpStatus.UNAUTHORIZED);
    }

}
