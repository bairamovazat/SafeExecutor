package ru.ivmiit.web.forms;

public enum SolutionStatus {
    CREATED(0), WRONG_ANSWER(-1), TIME_LIMIT_EXCEEDED(-1), COMPILATION_ERROR(-1), PROCESSED(0), ACCEPTED(1);
    private int status;
    private int getStatus(){
        return this.status;
    };

    SolutionStatus(int status){
        this.status = status;
    }

}
