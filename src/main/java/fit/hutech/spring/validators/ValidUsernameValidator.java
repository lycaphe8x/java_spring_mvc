package fit.hutech.spring.validators;

import fit.hutech.spring.repositories.IUserRepository;
import fit.hutech.spring.validators.annotations.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {
    private final IUserRepository userRepository;
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if(userRepository == null)
            return true;
        return userRepository.findByUsername(username) == null;
    }
}