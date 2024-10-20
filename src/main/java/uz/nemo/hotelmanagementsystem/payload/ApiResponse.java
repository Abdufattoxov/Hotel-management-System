package uz.nemo.hotelmanagementsystem.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
    private String message;
    private Boolean success;
    private T data;



    public ApiResponse<T> success(T data) {
        return new ApiResponse<>("Success", true, data);
    }

    public ApiResponse<T> success(String message) {
        return new ApiResponse<>(message, true, null);
    }

    public ApiResponse<Void> success() {
        return new ApiResponse<>("Success", true, null);
    }

    private ApiResponse(String message, Boolean success, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

}