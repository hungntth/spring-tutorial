package com.hungnt.hello_world.dto.request;

import com.hungnt.hello_world.exception.ErrorCode;
import com.hungnt.hello_world.validator.DobContraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults( level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
     String username;
    @Size(min = 6, message = "PASSWORD_INVALI")
     String password;
     String firstName;
     String lastName;

     @DobContraint(min = 18, message = "INVALID_DOB")
     LocalDate dob;
}
