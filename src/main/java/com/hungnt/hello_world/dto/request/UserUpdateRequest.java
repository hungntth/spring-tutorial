package com.hungnt.hello_world.dto.request;

import com.hungnt.hello_world.validator.DobContraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults( level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

     String password;
     String firstName;
     String lastName;

     @DobContraint(min = 18, message = "INVALID_DOB")
     LocalDate dob;

     Set<String> roles;

}
