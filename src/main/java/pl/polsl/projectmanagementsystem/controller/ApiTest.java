package pl.polsl.projectmanagementsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.management.api.controller.ApiApi;

@RestController
public class ApiTest implements ApiApi {
    @Override
    public ResponseEntity<Void> testOpenApi() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
