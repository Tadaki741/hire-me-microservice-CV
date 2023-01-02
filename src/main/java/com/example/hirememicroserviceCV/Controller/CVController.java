package com.example.hirememicroserviceCV.Controller;

import com.example.hirememicroserviceCV.Model.CV;
import com.example.hirememicroserviceCV.Repository.CVRepository;
import com.example.hirememicroserviceCV.Service.CVService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cv")
@CrossOrigin(origins = "*")
public class CVController {

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

    @GetMapping(path = "/test")
    public String test () {
        return "Test from CV controller";
    }

}
