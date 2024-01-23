package com.example.CourseWork.controller;

import com.example.CourseWork.dto.CollectorDTO;
import com.example.CourseWork.dto.responses.CollectorResponse;
import com.example.CourseWork.service.CollectorService;
import com.example.CourseWork.utils.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:0001")
@RestController
@RequestMapping("/api/collectors")
public class CollectorController {
    private CollectorService collectorService;

    public CollectorController(CollectorService collectorService) {
        this.collectorService = collectorService;
    }
    @GetMapping("")
    public CollectorResponse getAllCollectors(
            @RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
    {
        return collectorService.getAllCollectors(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectorDTO> getCollectorById(@PathVariable("id") Long id) {
        Optional<CollectorDTO> collectorDTO = Optional.ofNullable(collectorService.findCollectorById(id));
        if (collectorDTO.isPresent()) {
            return new ResponseEntity<>(collectorDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<CollectorDTO> createCollector(@RequestBody CollectorDTO collectorDTO) {
        return new ResponseEntity<>(collectorService.createCollector(collectorDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CollectorDTO> updateCollector(@PathVariable("id") Long id, @RequestBody CollectorDTO collectorDTO) {
        Optional<CollectorDTO> collectorData = Optional.ofNullable(collectorService.findCollectorById(id));
        if (collectorData.isPresent()) {
            collectorService.updateCollectorInfo(collectorDTO, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCollector(@PathVariable("id") Long id) {
        try {
            collectorService.deleteCollector(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
