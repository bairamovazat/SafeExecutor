package ru.ivmiit.executor;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Builder
@ToString
public class ExecutorResult {
    private String status;
    private Long cpuTime;
    private Long realTime;
    private Long vmSize;
    private String description;
    private Integer exitCode;
    private String programResult;
    private String allText;

    public boolean isOk(){
        return this.status != null && this.status.toLowerCase().equals("ok");
    }

    public boolean resultEquals(String outputData){
        return this.getProgramResult().trim().equals(outputData.trim());
    }

    public static ExecutorResult from(List<String> result){
        return from(String.join("\n", result));
    }

    public static ExecutorResult from(String result){
        return ExecutorResult.builder()
                .status(getTaskStatus(result))
                .cpuTime(getTaskCpuTime(result))
                .realTime(getTaskRealTime(result))
                .vmSize(getTaskVmSize(result))
                .description(getTaskDescription(result))
                .exitCode(getTaskExitCode(result))
                .programResult(getProgramResult(result))
                .allText(result)
                .build();
    }

    public static String getTaskStatus(String result){
        Pattern p = Pattern.compile("Status: ([\\w]{0,10})");
        Matcher m = p.matcher(result);
        if(m.find()){
            return m.group(1);
        }else {
            return null;
        }
    }

    public static Long getTaskCpuTime(String result){
        Pattern p = Pattern.compile("CPUTime: ([0-9]*)");
        Matcher m = p.matcher(result);
        if(m.find()){
            return Long.parseLong(m.group(1));
        }else {
            return null;
        }
    }

    public static Long getTaskRealTime(String result){
        Pattern p = Pattern.compile("RealTime: ([0-9]*)");
        Matcher m = p.matcher(result);
        if(m.find()){
            return Long.parseLong(m.group(1));
        }else {
            return null;
        }
    }

    public static Long getTaskVmSize(String result){
        Pattern p = Pattern.compile("VMSize: ([0-9]*)");
        Matcher m = p.matcher(result);
        if(m.find()){
            return Long.parseLong(m.group(1));
        }else {
            return null;
        }
    }

    public static String getTaskDescription(String result){
        Pattern p = Pattern.compile("Description: ([\\s\\S]*)(?:\\n|$)");
        Matcher m = p.matcher(result);
        if(m.find()){
            return m.group(1);
        }else {
            return null;
        }
    }

    public static Integer getTaskExitCode(String result){
        Pattern p = Pattern.compile("Exitcode: ([0-9]*)");
        Matcher m = p.matcher(result);
        if(m.find()){
            return Integer.parseInt(m.group(1));
        }else {
            return null;
        }
    }

    public static String getProgramResult(String result){
        Pattern p = Pattern.compile("task_Start: execv\\(1\\): (?:.\\/run|.\\/compiller)\\n([\\s\\S]*)Status");
        Matcher m = p.matcher(result);
        if(m.find()){
            return m.group(1);
        }else {
            return null;
        }
    }
}
