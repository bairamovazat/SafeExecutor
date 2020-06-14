package ru.ivmiit.web.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ivmiit.web.transfer.ExecutableDto;
import ru.ivmiit.web.transfer.SubmissionDto;

@Component
public class ExecutableDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.getName().equals(ExecutableDto.class.getName());
    }

    @Override
    public void validate(Object o, Errors errors) {
        ExecutableDto form = (ExecutableDto) o;

//        if(form.getId() == null){
//            errors.reject("bad.id", "Id задачи не может быть пустым!");
//        }

        int maxSize = 2048;
//        if (form.getCode().length() < psvm.length() + 1 || form.getCode().length() > maxSize) {
//            errors.reject("bad.code", "Блок кода должен быть от " + (psvm.length() + 1) + " до " + maxSize + " символов");
//        }
//
//        if (form.getCodeImport().length() > 512) {
//            errors.reject("bad.codeImport", "Блок импорта должены быть до 512 символов");
//        }
//
//        if(!form.getCode().contains(psvm) && !form.getCode().contains(psvmTwo)){
//            errors.reject("bad.code", "Блок кода должены содержать метод \"" + psvm +"\"");
//        }
    }

}
