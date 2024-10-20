package uz.nemo.hotelmanagementsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ApiErrorResponse {
    private Boolean success;
    private String message;
    private Object data;
    private LocalDateTime dateTime;

    public ApiErrorResponse(String message) {
        this.success = false;
        data = null;
        dateTime = LocalDateTime.now();
        this.message = message;
    }
}
