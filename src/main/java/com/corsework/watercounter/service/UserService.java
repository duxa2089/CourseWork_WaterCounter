package com.corsework.watercounter.service;

import com.corsework.watercounter.entities.User;
import com.corsework.watercounter.entities.dto.UserAuthDto;
import com.corsework.watercounter.repository.UserRepository;
import com.corsework.watercounter.utils.UserRole;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean createUser(UserAuthDto userAuthDto) {
        User user = new User();
        user.setEmail(userAuthDto.getEmail());
        user.setUsername(userAuthDto.getUsername());
        user.setPassword(userAuthDto.getPassword());
        user.setFirstName(userAuthDto.getFirstName());
        user.setLastName(userAuthDto.getLastName());
        user.setRole(UserRole.USER);
        user.setMiddleName(userAuthDto.getMiddleName() != null ? userAuthDto.getMiddleName() : null);

        userRepository.save(user);

        return true;

    }

    public User getUser(UserAuthDto userAuthDto) {

        return userRepository.findByUsername(userAuthDto.getUsername());

    }

    public ResponseEntity<byte[]> createPdf() throws FileNotFoundException, DocumentException, TransformerConfigurationException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk(userRepository.findAll().iterator().next().getUser_id().toString(), font);

        document.add(chunk);
        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);

        ResponseEntity<byte[]> response = new ResponseEntity(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
        return response;

    }
}
