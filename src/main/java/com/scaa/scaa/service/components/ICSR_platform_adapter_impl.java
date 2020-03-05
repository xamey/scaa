package com.scaa.scaa.service.components;

import com.google.gson.Gson;
import com.scaa.scaa.model.Application;
import com.scaa.scaa.model.Component;
import com.scaa.scaa.model.ComponentServiceRelation;
import com.scaa.scaa.service.components.dto.ComponentDto;
import com.scaa.scaa.service.components.dto.FileDto;
import com.scaa.scaa.service.components.dto.ServiceDto;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ICSR_platform_adapter_impl implements ICSR_platform_adapter {
    @Override
    public List<Application> getApplicationsFromPlatform() {
        return null;
    }

    @Override
    public List<ComponentServiceRelation> getComponentServiceRelationListFromPlatform() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("platform.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileDto fileDto = new Gson().fromJson(br, FileDto.class);
        List<ComponentDto> componentDto = Arrays.asList(fileDto.getComponents());
        List<ComponentServiceRelation> list = new ArrayList<>();
        for (ComponentDto currentCDTO : componentDto) {
            for (ServiceDto s : currentCDTO.getServices()) {
                com.scaa.scaa.model.Service new_s = new com.scaa.scaa.model.Service(s.getName().toLowerCase(), s.getDescription());
                Component new_c = new Component(currentCDTO.getName().toLowerCase());
                ComponentServiceRelation csr = new ComponentServiceRelation(new_c, new_s, s.isRequired());
                list.add(csr);
            }
        }
        return list;
    }

    @Override
    public void setApplicationToPlatform(Application application) {

    }
}
