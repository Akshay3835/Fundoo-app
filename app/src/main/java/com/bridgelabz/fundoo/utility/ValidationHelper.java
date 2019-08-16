package com.bridgelabz.fundoo.utility;

import java.util.regex.Pattern;

public class ValidationHelper
{
    private static final Pattern EMAIL_PATTERN = Pattern.compile( "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,})$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})");

    public static boolean Validate_Email(String email)
    {
        if (email.isEmpty())
        {
            return false;
        }
        else if (!EMAIL_PATTERN.matcher(email).matches())
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public static boolean Validate_Password(String password)
    {

        return !password.isEmpty() && PASSWORD_PATTERN.matcher(password).matches();

    }
    public static Boolean validateDescription(String description)
    {

        if (description.isEmpty())
        {

            return false;
        }
        else
        return true;
    }

    public static Boolean validateTitle(String title)
    {

        if (title.isEmpty())
        {
//            Toast.makeText(ValidationHelper.this , "  ", Toast.LENGTH_SHORT).show();

            return false;
        }
        else
//            Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        return true;
    }

}
