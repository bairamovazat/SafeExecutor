package ru.ivmiit.executor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Builder
@ToString(exclude = {"allText"})
public class EjudgeResult {
    public String status;
    public Long cpuTime;
    public Long realTime;
    public Long vmSize;
    public String description;
    public Integer exitCode;

    public String allText;

    public static EjudgeResult from(List<String> result){
        return from(String.join("\n", result));
    }

    public static EjudgeResult from(String result){
        return EjudgeResult.builder()
                .status(getTaskStatus(result))
                .cpuTime(getTaskCpuTime(result))
                .realTime(getTaskRealTime(result))
                .vmSize(getTaskVmSize(result))
                .description(getTaskDescription(result))
                .exitCode(getTaskExitCode(result))
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
        Pattern p = Pattern.compile("Description: ([.]*)\\n");
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
}
