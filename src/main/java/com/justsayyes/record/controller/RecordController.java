package com.justsayyes.record.controller;
import com.justsayyes.record.DTO.*;
import com.justsayyes.record.service.EmailService;
import com.justsayyes.record.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

@RestController
@RequestMapping("/record")
@Api("controller for uploading record and test results")
@Retryable(value = Exception.class, maxAttempts = 3)
public class RecordController {

    @Autowired
    WebApplicationContext applicationContext;

    @ApiOperation(value = "Upload location info of diner and get id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class)
    })
    @RequestMapping(
            value = "/getIndexForLocations",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> getIndexForLocations(@RequestBody LocationInfoDTO locationInfoDTO) {
        return applicationContext.getBean(RecordService.class).getLocationIndex(locationInfoDTO);
    }

    @ApiOperation(value = "Upload visitor record, timestamp is optional, in milliseconds UTC ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Boolean.class),
            @ApiResponse(code = 400, message = "bad request", response = Boolean.class)
    })
    @RequestMapping(
            value = "/uploadRecord",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> uploadRecord(@RequestBody RecordDTO recordDTO) {
        return applicationContext.getBean(RecordService.class).uploadRecord(recordDTO);
    }

    @ApiOperation(value = "Upload covid test result ( positive or negative ) timestamp is optional,in milliseconds UTC")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = TestDTO.class),
            @ApiResponse(code = 400, message = "bad request", response = TestDTO.class)
    })
    @RequestMapping(
            value = "/uploadStatus",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> uploadStatus(@RequestBody StatusDTO statusDTO) {
        return applicationContext.getBean(RecordService.class).uploadStatus(statusDTO);
    }

    @ApiOperation(value = "getHeapMap of all time, active cases in timestamp for every location")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = HeatMapDTO.class),
            @ApiResponse(code = 400, message = "bad request", response = HeatMapDTO.class)
    })
    @RequestMapping(
            value = "/getHeatMapByActiveCases",
            method = {RequestMethod.GET},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> getHeatMapByActiveCases() {
        return applicationContext.getBean(RecordService.class).getHeatMap("ACTIVE");
    }

    @ApiOperation(value = "getHeapMap of all time, active cases in timestamp for every location")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = HeatMapDTO.class),
            @ApiResponse(code = 400, message = "bad request", response = HeatMapDTO.class)
    })
    @RequestMapping(
            value = "/getHeatMapByExposedCases",
            method = {RequestMethod.GET},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> getHeatMapByExposedCases() {
        return applicationContext.getBean(RecordService.class).getHeatMap("EXPOSED");
    }

    @ApiOperation(value = "get daily cases")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = DailyCasesRetDTO.class),
            @ApiResponse(code = 400, message = "bad request", response = DailyCasesRetDTO.class)
    })
    @RequestMapping(
            value = "/getDailyCases",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> getDailyCases(@RequestBody DailyCasesDTO dailyCasesDTO) {
        return applicationContext.getBean(RecordService.class).getDailyCases(dailyCasesDTO);
    }

    @ApiOperation(value = "get status")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class),
            @ApiResponse(code = 400, message = "bad request", response = String.class)
    })
    @RequestMapping(
            value = "/getStatus",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> getStatus(@RequestBody GetStatusDTO getStatusDTO) {
        return applicationContext.getBean(RecordService.class).getStatus(getStatusDTO);
    }

    @ApiOperation(value = "testEmail")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class),
            @ApiResponse(code = 400, message = "bad request", response = String.class)
    })
    @RequestMapping(
            value = "/testEmail",
            method = {RequestMethod.GET},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> testEmail() {
        applicationContext.getBean(EmailService.class).sendSimpleMessage("coredroid0401@gmail.com","test IMAP","COVID EXPOSED");
        return new ResponseEntity<>("0", HttpStatus.OK);
    }

    @ApiOperation(value = "getCasesByDay")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = getCasesByDayDTO.class),
            @ApiResponse(code = 400, message = "bad request", response = getCasesByDayDTO.class)
    })
    @RequestMapping(
            value = "/getCasesByDay",
            method = {RequestMethod.GET},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> getCasesByDay() {
        return new ResponseEntity<>(applicationContext.getBean(RecordService.class).getCasesByDate(), HttpStatus.OK);
    }
}
