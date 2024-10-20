package uz.nemo.hotelmanagementsystem.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;
import uz.nemo.hotelmanagementsystem.entity.Hotel;
import uz.nemo.hotelmanagementsystem.entity.Reservation;
import uz.nemo.hotelmanagementsystem.entity.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {
    public PdfService() {
    }

    public ByteArrayInputStream generatePdfForUserHotelOrders(List<User> users, List<Hotel> hotels, List<Reservation> reservations) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            document.add(new Paragraph("User, Hotel, and Order Information").setBold()).setFontSize(18.0F);
            document.add(new Paragraph("Users:"));
            Table userTable = new Table(4);
            userTable.addCell("ID");
            userTable.addCell("Name");
            userTable.addCell("PhoneNumber");
            userTable.addCell("Email");

            for (User user : users) {
                userTable.addCell(String.valueOf(user.getId()));
                userTable.addCell(user.getFirstName());
                userTable.addCell(user.getPhoneNumber());
                userTable.addCell(user.getEmail());
            }

            document.add(userTable);
            document.add(new Paragraph("Hotels:"));
            Table hotelTable = new Table(4);
            hotelTable.addCell("ID");
            hotelTable.addCell("Name");
            hotelTable.addCell("Location");
            hotelTable.addCell("Number of rooms");

            for (Hotel hotel : hotels) {
                hotelTable.addCell(String.valueOf(hotel.getId()));
                hotelTable.addCell(hotel.getName());
                hotelTable.addCell(hotel.getLocation());
                hotelTable.addCell(String.valueOf(hotel.getRooms().size()));
            }

            document.add(hotelTable);
            document.add(new Paragraph("Orders:"));
            Table orderTable = new Table(6);
            orderTable.addCell("Order ID");
            orderTable.addCell("User ID");
            orderTable.addCell("Hotel ID");
            orderTable.addCell("Room ID");
            orderTable.addCell("CheckInDate");
            orderTable.addCell("CheckOutDate");

            for (Reservation order : reservations) {
                orderTable.addCell(String.valueOf(order.getId()));
                orderTable.addCell(String.valueOf(order.getUser().getId()));
                orderTable.addCell(String.valueOf(order.getRoom().getId()));
                orderTable.addCell(String.valueOf(order.getRoom().getHotel().getId()));
                orderTable.addCell(String.valueOf(order.getCheckInDate()));
                orderTable.addCell(String.valueOf(order.getCheckOutDate()));
            }

            document.add(orderTable);
            document.close();
        } catch (Exception var13) {
            var13.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
