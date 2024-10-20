package uz.nemo.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nemo.hotelmanagementsystem.entity.Reservation;
import uz.nemo.hotelmanagementsystem.entity.User;
import uz.nemo.hotelmanagementsystem.entity.Hotel;
import uz.nemo.hotelmanagementsystem.repository.ReservationRepository;
import uz.nemo.hotelmanagementsystem.service.PdfService;
import uz.nemo.hotelmanagementsystem.repository.UserRepository;
import uz.nemo.hotelmanagementsystem.repository.HotelRepository;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pdf")
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final ReservationRepository orderRepository;

    @Operation(summary = "Download PDF Report", description = "Generates and downloads a PDF containing information about users, hotels, and reservations.")
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPdf() {
        List<User> users = userRepository.findAll();
        List<Hotel> hotels = hotelRepository.findAll();
        List<Reservation> orders = orderRepository.findAll();

        ByteArrayInputStream pdfStream = pdfService.generatePdfForUserHotelOrders(users, hotels, orders);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hotel_info.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream.readAllBytes());
    }
}
