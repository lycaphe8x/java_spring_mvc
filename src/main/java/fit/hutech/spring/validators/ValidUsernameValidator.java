package fit.hutech.spring.validators;

import fit.hutech.spring.services.UserService;
import fit.hutech.spring.validators.annotations.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {
    private final UserService userService;
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return userService.findByUsername(username).isEmpty();
    }
}