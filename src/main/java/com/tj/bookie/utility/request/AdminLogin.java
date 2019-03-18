package com.tj.bookie.utility.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLogin {
    @NotEmpty
    String username;

    @NotEmpty
    String pwd;
}
