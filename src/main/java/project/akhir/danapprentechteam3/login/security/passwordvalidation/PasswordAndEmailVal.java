package project.akhir.danapprentechteam3.login.security.passwordvalidation;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PasswordAndEmailVal
{
    private Pattern pattern;
    private Matcher matcher;

    // "^[0-9]+$"
    private static final String PASSWORD_PATTERN_NUMBER     =
            ".*\\d.*";
    private static final String NUMBER_ONLY                 =
            "\\+?([ -]?\\d+)+|\\(\\d+\\)([ -]\\d+)";
    private static final String PASSWORD_PATTERN_LOWERCASE  =
            ".*[a-z].*";
    private static final String PASSWORD_PATTERN_UPPERCASE  =
            ".*[A-Z].*";
    private static final String PASSWORD_PATTERN_SYMBOL     =
            ".*[@#$%!].*";
    private static final String EMAIL_REGEX                 =
            "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    public boolean PasswordValidatorLowercase(String password)
    {
        pattern = Pattern.compile(PASSWORD_PATTERN_LOWERCASE);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean PasswordValidatorUpercase(String password)
    {
        pattern = Pattern.compile(PASSWORD_PATTERN_UPPERCASE);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean PasswordValidatorNumber(String password){
        pattern = Pattern.compile(PASSWORD_PATTERN_NUMBER);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean PasswordValidatorSymbol(String password){
        pattern = Pattern.compile(PASSWORD_PATTERN_SYMBOL);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean confirmPassword(String password, String confirmPassword){
        boolean status = false;
        if (password.equals(confirmPassword))
        {
            status = true;
        }
        return status;
    }

    public boolean EmailValidator (String password){
        pattern = Pattern.compile(EMAIL_REGEX);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean NumberOnlyValidator (String number){
        pattern = Pattern.compile(NUMBER_ONLY);
        matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public boolean LengthPhoneNumber (String number){
        boolean status;
        if (number.length() >= 13){
            return status = false;
        }
            return status = true;
    }

    public boolean PasswordValidatorSpace (String password){
        if(password != null){
            for(int i = 0; i < password.length(); i++){
                if(Character.isWhitespace(password.charAt(i))){
                    return false;
                }
            }
        }
        return true;
    }

}
