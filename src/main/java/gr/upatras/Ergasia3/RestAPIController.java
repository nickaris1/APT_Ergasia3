package gr.upatras.Ergasia3;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class RestAPIController {
    private static final Logger log = LoggerFactory.getLogger( RestAPIController.class);

    @RequestMapping(value = "/temp", produces = { "application/json;charset=utf-8" }, consumes = {"application/json;charset=utf-8" }, method = RequestMethod.POST)
    public ResponseEntity<Temperature> createProduct(@RequestBody Temperature temp) {
        log.info( "Will send a new temp: " + Double.toString(temp.getTemperature()) );
        MQTTPublisher mqttPublisher = new MQTTPublisher("gr/upatras/ergasia3/up1066471/");
        mqttPublisher.sendMqttMessage(temp.getTemperature());

        return new ResponseEntity<>(temp, HttpStatus.OK);
    }
}