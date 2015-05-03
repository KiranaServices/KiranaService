package com.kirana.controller.utils;

import com.kirana.model.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegisterParamValidator implements Validator {

//	 private static final Logger logger = LoggerFactory.getLogger(LoginParamValidator.class);
    @Override
    public boolean supports(Class<?> classObject) {
        return classObject == User.class;
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User regPar = (User) obj;

        if (regPar.getUserName() == null || regPar.getUserName().trim().length() == 0) {
            errors.rejectValue("UserName", "username.required", "username parameter issing");
        } else if (regPar.getPass() == null || regPar.getPass().trim().length() == 0) {
            errors.rejectValue("PassWord", "password.required", "password missing");
        } else if (regPar.getEmail() == null || regPar.getEmail().trim().length() == 0) {
            errors.rejectValue("Email", "Email.required", "Email missing");
        } else if (regPar.getPhone() == null || regPar.getPhone().trim().length() == 0) {
            errors.rejectValue("Number", "Number.required", "Number missing");
        } else if (regPar.getState() == null || regPar.getState().trim().length() == 0) {
            errors.rejectValue("State", "State.required", "State missing");
        } else if (regPar.getStreet() == null || regPar.getStreet().trim().length() == 0) {
            errors.rejectValue("Street", "Street.required", "Street missing");
        } else if (regPar.getTIN() == null || regPar.getTIN().trim().length() == 0) {
            errors.rejectValue("TIN", "TIN.required", "TIN missing");
        } else if (regPar.getWebsite() == null || regPar.getWebsite().trim().length() == 0) {
            errors.rejectValue("Website", "Website.required", "Website missing");
        }
    }
}
