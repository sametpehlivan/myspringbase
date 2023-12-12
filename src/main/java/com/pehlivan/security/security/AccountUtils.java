package com.pehlivan.security.security;


import org.passay.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountUtils {

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    public static final int MAX_ATTEMPT_SIZE = 5;
    public static final int LOCK_TIME_MINUTE = 3;
    public static final DateTimeFormatter LOCK_DATE_TIME_FORMAT =  DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static final String  BIRTH_DAY_FORMAT_VALUE = "dd-MM-yyyy";
    public static final DateTimeFormatter BIRTH_DAY_FORMAT = DateTimeFormatter.ofPattern(BIRTH_DAY_FORMAT_VALUE);
    public static final int RESET_LOCK_DATE_HOUR = 24;
}
