package com.corsework.watercounter.entities.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class RoomDto {

    private String size;

    private String address;

    private List<IndicatorDto> indicatorDto;
}
