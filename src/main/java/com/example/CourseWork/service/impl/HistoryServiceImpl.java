package com.example.CourseWork.service.impl;

import com.example.CourseWork.dto.HistoryDTO;;
import com.example.CourseWork.dto.responses.HistoryResponse;
import com.example.CourseWork.model.History;
import com.example.CourseWork.repository.HistoryRepository;
import com.example.CourseWork.service.HistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {
    private HistoryRepository historyRepository;

    private HistoryDTO toHistoryDTO(History history){
        HistoryDTO historyDTO;
        historyDTO = new HistoryDTO(history.getHistoryBody());
        return historyDTO;
    }

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public HistoryDTO createHistory(HistoryDTO historyDTO) {
        History history = new History();
        history.setHistoryBody(historyDTO.historyBody());
        return toHistoryDTO(historyRepository.save(history));
    }

    @Override
    public HistoryDTO findHistoryById(Long id) {
        Optional<History> history = historyRepository.findById(id);
        if(history.isPresent()) {
            return toHistoryDTO(history.get());
        }
        return null;
    }

    @Override
    public HistoryDTO updateHistoryInfo(HistoryDTO historyDTO, Long id) {
        Optional<History> history = historyRepository.findById(id);
        if (history.isPresent()) {
            History historyModel = history.get();
            historyModel.setHistoryBody(historyDTO.historyBody());
            return toHistoryDTO(historyRepository.save(historyModel));
        }
        return null;
    }

    @Override
    public HistoryDTO deleteHistory(Long id) {
        Optional<History> historyData = historyRepository.findById(id);
        if (historyData.isPresent()) {
            historyRepository.delete(historyData.get());
        }
        return null;
    }

    @Override
    public HistoryResponse getAllHistory(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<History> histories = historyRepository.findAll(pageable);
        List<History> listOfHistories = histories.getContent();

        List<HistoryDTO> content = listOfHistories.stream().map(history -> toHistoryDTO(history)).collect(Collectors.toList());
        HistoryResponse historyResponse = new HistoryResponse();
        historyResponse.setContent(content);
        historyResponse.setPageNo(histories.getNumber());
        historyResponse.setPageSize(histories.getSize());
        historyResponse.setTotalElements(histories.getTotalElements());
        historyResponse.setTotalPages(histories.getTotalPages());
        historyResponse.setLast(histories.isLast());

        return historyResponse;
    }
}
