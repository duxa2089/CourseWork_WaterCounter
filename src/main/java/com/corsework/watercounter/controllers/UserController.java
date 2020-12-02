package com.corsework.watercounter.controllers;

import com.corsework.watercounter.entities.Room;
import com.corsework.watercounter.entities.User;
import com.corsework.watercounter.entities.dto.IndicateDto;
import com.corsework.watercounter.entities.dto.RoomDto;
import com.corsework.watercounter.entities.dto.UserAuthDto;
import com.corsework.watercounter.service.IndicatorService;
import com.corsework.watercounter.service.RoomService;
import com.corsework.watercounter.service.UserService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.TransformerConfigurationException;
import java.io.FileNotFoundException;

@Slf4j
@RestController("userController")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private RoomService roomService;

    @PostMapping(value = "/registry")
    public boolean registry(@RequestBody UserAuthDto userAuthDto) {

        return userService.createUser(userAuthDto);
    }

    @PostMapping(value = "/get")
    public User getUser(@RequestBody UserAuthDto userAuthDto) {

        return userService.getUser(userAuthDto);
    }

    @PostMapping(value = "/indicate")
    public boolean setIndicate(@RequestBody IndicateDto indicateDto) {

        return indicatorService.setIndicate(indicateDto);
    }

    @PostMapping(value = "/rooms")
    public Iterable<Room> getAllRooms() {

        return roomService.getAllRooms();
    }

    @GetMapping(value = "/room")
    public Room getRoom(@RequestParam String username) {

        return roomService.getRoomByUsername(username);
    }

    @GetMapping(value = "/createroom")
    public boolean createRoom(@RequestBody RoomDto roomDto) {

        return roomService.createRoom(roomDto);
    }

    @GetMapping(value = "/confirmed")
    public boolean confirmedHistory(@RequestParam String historyId) {

        return indicatorService.confirmedIndicator(historyId);
    }

    @GetMapping(value = "/pdf")
    public ResponseEntity<byte[]> getPdf() throws FileNotFoundException, DocumentException, TransformerConfigurationException {

        return userService.createPdf();
    }
}
