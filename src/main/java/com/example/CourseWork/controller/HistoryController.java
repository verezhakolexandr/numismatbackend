package com.example.CourseWork.controller;

import com.example.CourseWork.dto.HistoryDTO;
import com.example.CourseWork.dto.responses.HistoryResponse;
import com.example.CourseWork.service.HistoryService;
import com.example.CourseWork.utils.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:0001")
@RestController
@RequestMapping("/api/histories")
public class HistoryController {
    private HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }
    @GetMapping("")
    public HistoryResponse getAllHistories(
            @RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
    {
        return historyService.getAllHistory(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoryDTO> getHistoryById(@PathVariable("id") Long id) {
        Optional<HistoryDTO> historyDTO = Optional.ofNullable(historyService.findHistoryById(id));
        if (historyDTO.isPresent()) {
            return new ResponseEntity<>(historyDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<HistoryDTO> createHistory(@RequestBody HistoryDTO historyDTO) {
        return new ResponseEntity<>(historyService.createHistory(historyDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<HistoryDTO> updateHistory(@PathVariable("id") Long id, @RequestBody HistoryDTO historyDTO) {
        Optional<HistoryDTO> historyData = Optional.ofNullable(historyService.findHistoryById(id));
        if (historyData.isPresent()) {
            historyService.updateHistoryInfo(historyDTO, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteHistory(@PathVariable("id") Long id) {
        try {
            historyService.deleteHistory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
