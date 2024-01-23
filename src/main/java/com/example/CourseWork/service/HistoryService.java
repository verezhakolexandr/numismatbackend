package com.example.CourseWork.service;

import com.example.CourseWork.dto.HistoryDTO;
import com.example.CourseWork.dto.responses.HistoryResponse;
import org.springframework.stereotype.Service;

@Service
public interface HistoryService {
    HistoryDTO createHistory(HistoryDTO historyDTO);

    HistoryDTO findHistoryById(Long id);

    HistoryDTO updateHistoryInfo(HistoryDTO historyDTO, Long id);

    HistoryDTO deleteHistory(Long id);

    HistoryResponse getAllHistory(int pageNo, int pageSize, String sortBy, String sortDir);
}
