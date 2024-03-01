package com.clientui.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class GetAge {
    public static int calculateAge(Date birthdate){
        LocalDate birthdateLocalDate = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthdateLocalDate, currentDate).getYears();
    }
}
