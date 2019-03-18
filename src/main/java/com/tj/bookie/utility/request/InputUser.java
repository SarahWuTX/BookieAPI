package com.tj.bookie.utility.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputUser {

    @NotEmpty
    private String wx_id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;

    private String birthday;

    private List<String> address;

}