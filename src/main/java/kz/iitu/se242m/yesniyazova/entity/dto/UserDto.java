package kz.iitu.se242m.yesniyazova.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.iitu.se242m.yesniyazova.validator.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String username;
    @PhoneNumber
    private String phoneNumber;
    private String email;
    private String role;
    private boolean isActive;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;
}
