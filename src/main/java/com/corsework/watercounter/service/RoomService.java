package com.corsework.watercounter.service;

import com.corsework.watercounter.entities.Indicator;
import com.corsework.watercounter.entities.Room;
import com.corsework.watercounter.entities.dto.RoomDto;
import com.corsework.watercounter.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Iterable<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomByUsername(String username) {
        return roomRepository.findByUsername(username);
    }

    public boolean createRoom(RoomDto roomDto) {

        Room room = new Room();
        room.setAddress(roomDto.getAddress());
        roomDto.setSize(roomDto.getSize());

        List<Indicator> indicatorList = roomDto.getIndicatorDto().stream()
                .map(item -> {
                    Indicator indicator = new Indicator();
                    indicator.setFirm(item.getFirm());
                    indicator.setGuarantee(item.getGuarantee());
                    indicator.setModel(item.getModel());
                    indicator.setTechParam(item.getTechParam());
                    return indicator;
                }).collect(Collectors.toList());

        room.setIndicators(indicatorList);

        roomRepository.save(room);

        return true;
    }
}
