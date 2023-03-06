package com.example.opentalk.mapper;

import com.example.opentalk.dto.CreateOpentalkRequest;
import com.example.opentalk.dto.OpentalkDTo;
import com.example.opentalk.entity.Opentalk_topic;
import com.example.opentalk.entity.Status;
import com.example.opentalk.repository.OpentalkRepository;
import com.example.opentalk.repository.StatusRepository;
import com.example.opentalk.service.AuthService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class OpentalkMapper {
    private final OpentalkRepository opentalkRepository;
    private final AuthService authService;
    private final StatusRepository statusRepository;

    public OpentalkDTo MapDataToDTO(Opentalk_topic opentalk){
        return OpentalkDTo.builder()
                .name(opentalk.getName())
                .id(opentalk.getId())
                .employeeName(opentalk.getEmployee().getName())
                .status(opentalk.getStatus().getName())
                .time(opentalk.getTime().toString())
                .company_branch_name(opentalk.getEmployee().getCompany_branch().getBranch_Name())
                .build();
    }

    public Opentalk_topic MapDtoUpdateToData(CreateOpentalkRequest createOpentalkRequest){
        Opentalk_topic opentalk = opentalkRepository.getById(createOpentalkRequest.getId());
        Status status = statusRepository.getStatusById(1L);
        status.setName("hellfsdf");
        opentalk.setTime(createOpentalkRequest.getTime());
        opentalk.setName(createOpentalkRequest.getName());
        opentalk.setEmployee(authService.getCurrentUser());
        return opentalk;
    }

    public Opentalk_topic MapDtoCreateToData(CreateOpentalkRequest createOpentalkRequest){
        return Opentalk_topic.builder()
                .time(createOpentalkRequest.getTime())
                .name(createOpentalkRequest.getName())
                .employee(authService.getCurrentUser())
                .status(statusRepository.getStatusById(1L))
                .build();
    }
}
