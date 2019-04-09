package ru.ivmiit.web.forms;

public enum SolutionStatus {
    CREATED(0), WRONG_ANSWER(-1), TEST_ERROR(-1), TIME_LIMIT_EXCEPTED(-1), MEMORY_EXCEPTED(-1), COMPILATION_ERROR(-1), PROCESSED(0), ACCEPTED(1);
    private int status;
    private int getStatus(){
        return this.status;
    };

    SolutionStatus(int status){
        this.status = status;
    }

    public static SolutionStatus fromString(String status){
        switch (status.toUpperCase()){
            case "OK":
                return ACCEPTED;
            case "TL":
                return TIME_LIMIT_EXCEPTED;
                //TODO проверить
            case "ME" :
                return MEMORY_EXCEPTED;
                default:
                    return null;
        }
    }
}
