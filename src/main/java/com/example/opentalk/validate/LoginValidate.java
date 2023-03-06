package com.example.opentalk.validate;

import com.example.opentalk.model.Status;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoginValidate {
    private static final String USERNAME_PATTERN = "^[a-z0-9._-]{6,15}$";
    private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,50}";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static void passwordValidate(String password) throws Exception{
        if(!password.matches(PASSWORD_PATTERN))
            throw new Status(HttpStatus.BAD_REQUEST, "Mật khẩu phải chứa ký tự viết hoa, viết thường, chữ số, ký tự đặc biệt và lớn hơn 8");
    }

    public static void usernameValidate(String username) throws Exception{
        if(!username.matches(USERNAME_PATTERN))
            throw new Status(HttpStatus.BAD_REQUEST, "có độ dài lớn hơn 6 bé hơn 15 và chỉ chứa các kí tự a-z, 0-9, _, - hoặc .");
    }

    public static void emailValidate(String emailStr) throws Exception{
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        if(!matcher.find())
            throw new Status(HttpStatus.BAD_REQUEST, "email không hợp lệ");
    }
}
