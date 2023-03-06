package com.example.opentalk.service;

import com.example.opentalk.dto.CreateOpentalkRequest;
import com.example.opentalk.dto.OpentalkDTo;
import com.example.opentalk.entity.Employee;
import com.example.opentalk.entity.Opentalk_topic;
import com.example.opentalk.mapper.OpentalkMapper;
import com.example.opentalk.model.Status;
import com.example.opentalk.repository.OpentalkRepository;
import com.example.opentalk.repository.StatusRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OpentalkService {

    private final OpentalkRepository opentalkRepository;
    private final AuthService authService;
    private final OpentalkMapper opentalkMapper;
    private final StatusRepository statusRepository;

//    public OpentalkService(OpentalkRepository opentalkRepository, AuthService authService, OpentalkMapper opentalkMapper, StatusRepository statusRepository) {
//        this.opentalkRepository = opentalkRepository;
//        this.authService = authService;
//        this.opentalkMapper = opentalkMapper;
//        this.statusRepository = statusRepository;
//    }

//
//    public void setOpentalkRepository(OpentalkRepository opentalkRepository) {
//        this.opentalkRepository = opentalkRepository;
//    }

    public Status signupOpentalk(CreateOpentalkRequest createOpentalkRequest) {
        try{
            if(opentalkRepository.findOpentalk(createOpentalkRequest.getTime(), null) != null)
                return new Status(HttpStatus.BAD_REQUEST, "Lịch này đã có người đăng ký");
            opentalkRepository.save(opentalkMapper.MapDtoCreateToData(createOpentalkRequest));
            return new Status(HttpStatus.OK, "Đăng ký thành công");
        }catch (Exception e){
            System.out.println(e);
            return new Status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Status updateOpentalk(CreateOpentalkRequest createOpentalkRequest) {
        try{
            if(opentalkRepository.findOpentalk(createOpentalkRequest.getTime(), createOpentalkRequest.getId()) != null)
                return new Status(HttpStatus.BAD_REQUEST, "Lịch này đã có người đăng ký");
//            com.example.opentalk.entity.Status status = statusRepository.getStatusById(1L);
            opentalkRepository.save(opentalkMapper.MapDtoUpdateToData(createOpentalkRequest));
//            status.getOpentalk_topics().forEach(e -> System.out.println(e.getName()));
            return new Status(HttpStatus.OK, "Thành công");
        }catch (Exception e){
            return new Status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Status deleteOpentalk(Long id){
        try{
            Employee employee = authService.getCurrentUser();
            Opentalk_topic opentalk_topic = opentalkRepository.getById(id);
            if(employee.getId() != opentalk_topic.getEmployee().getId()){
                return new Status(HttpStatus.FORBIDDEN, "You don't have permission to delete");
            }
            opentalkRepository.delete(opentalk_topic);

            return new Status(HttpStatus.OK, "Success");
        }catch (Exception e){
            return new Status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public List<OpentalkDTo> getAllOpentalk(int page, int limit, Long statusID, String owner, Date time_start, Date time_last, String company) {
        Pageable paging = PageRequest.of(page, limit);
        return opentalkRepository.filterOpentalk(paging, owner, statusID, time_start, time_last, company)
                .stream()
                .map(opentalkMapper::MapDataToDTO)
                .collect(Collectors.toList());
    }

    public List<OpentalkDTo> getAll() {
        return opentalkRepository.getAll().stream().map(opentalkMapper::MapDataToDTO).collect(Collectors.toList());
    }
}
