package com.erling.daoJ.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private int id;
    private String username;
    private boolean isAdmin;

}
