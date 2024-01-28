package com.edirnegezgini.commonservice.util;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CustomModelMapper extends ModelMapper {
    public <T,D> List<T> convertList(List<D> sourceList, Class<T> targetClass){
        return sourceList.stream()
                .map(element-> map(element,targetClass))
                .collect(Collectors.toList());
    }
}
