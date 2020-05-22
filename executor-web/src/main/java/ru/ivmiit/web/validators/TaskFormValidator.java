package ru.ivmiit.web.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ivmiit.web.transfer.ProblemDto;

@Component
public class TaskFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.getName().equals(ProblemDto.class.getName());
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProblemDto form = (ProblemDto) o;
//        form.trim();

        if(form.getName().length() < 3 ||  form.getName().length() > 128){
            errors.reject("bad.name", "Логин должен быть от 3 до 128 символов");
        }

        if(form.getDescription().length() < 3 ||  form.getDescription().length() > 512){
            errors.reject("bad.description", "Описание должены быть от 3 до 512 символов");
        }

//        if(form.getTestList().size() <= 0){
//            errors.reject("bad.testList", "Нужно добавть минимум 1 тест");
//        }
    }
}
