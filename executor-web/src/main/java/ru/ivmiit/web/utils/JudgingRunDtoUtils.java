package ru.ivmiit.web.utils;

import lombok.extern.slf4j.Slf4j;
import ru.ivmiit.domjudge.connector.transfer.AddJudgingRunDto;
import ru.ivmiit.domjudge.connector.utils.Base64Utils;
import ru.ivmiit.web.model.CompletedTestCase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JudgingRunDtoUtils {

    private JudgingRunDtoUtils() {
    }

    public static Map<String, String> parseMetaData(String data) {
        HashMap<String, String> metaDataMap = new HashMap<>();
        Arrays.stream(data.split("\n")).forEach(row -> {
            String[] rowData = row.split("\\: ");
            if (rowData.length == 1) {
                metaDataMap.put(rowData[0], null);
            } else if (rowData.length == 2) {
                metaDataMap.put(rowData[0], rowData[1]);
            } else {
                log.error("Ошибка обработки metaData, текущя строка пустая. MetaData:" + data);
            }
        });
        return metaDataMap;
    }

    public static void fillFillDataFrom(AddJudgingRunDto from, CompletedTestCase to) {
        String originalOutputRun = Base64Utils.decodeFromUrl(from.getOutputRun());
        String originalOutputDiff = Base64Utils.decodeFromUrl(from.getOutputDiff());

        String originalOutputError = Base64Utils.decodeFromUrl(from.getOutputError());

        String originalOutputSystem = Base64Utils.decodeFromUrl(from.getOutputSystem());
        String originalMetaData = Base64Utils.decodeFromUrl(from.getMetaData());

        Map<String, String> metadataMap =
                JudgingRunDtoUtils.parseMetaData(originalMetaData);

        to.setCpuTime(from.getRuntime());

        to.setOutputRun(originalOutputRun);
        to.setOutputDiff(originalOutputDiff);
        to.setOutputError(originalOutputError);
        to.setOutputSystem(originalOutputSystem);
        to.setMetaData(originalMetaData);

        String memoryUsage = metadataMap.get("memory-bytes");
        if (memoryUsage != null) {
            try {
                to.setMemoryUsed(Long.parseLong(memoryUsage));
            } catch (NumberFormatException e) {
                log.error("Ошибка извлечения memory-bytes", e);
            }
        }
    }

}
