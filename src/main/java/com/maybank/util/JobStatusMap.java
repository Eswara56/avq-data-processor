package com.maybank.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JobStatusMap {

    private static final Map<String, Map<String, Status>> calcMap = new ConcurrentHashMap<>();

    //Create new system code in map
    public static void createSystemCodeMap(String key) {
        calcMap.put(key, new ConcurrentHashMap<>());
    }

    /**
     * Get the value from the map.
     * @param key
     * @return
     */
    public static Map<String, Status> getSystemCodeMap(String key) {
        return calcMap.get(key);
    }

    //Get System code map and update the value
    public static void updateValue(String key, String key2, Status value) {
        Map<String, Status> map = calcMap.get(key);
        if (map != null) {
            map.put(key2, value);
        } else {
            map = new ConcurrentHashMap<>();
            map.put(key2, value);
            calcMap.put(key, map);
        }
    }

    //get the percentage from the given system code
    public static Status getPercentage(String key) {
        Map<String, Status> map = calcMap.get(key);
        Status status = new Status();
        Double percentage = 0.0;
        if (map != null) {
            for (Map.Entry<String, Status> entry : map.entrySet()) {
                percentage += entry.getValue().getPercentage();
            }
            if (percentage >= 100.0) {
                status.setMessageType(StatusMessageType.COMPLETED.getValue());
            } else {
                status.setMessageType(StatusMessageType.IN_PROGRESS.getValue());
            }
            status.setPercentage(percentage);
        } else {
            status.setMessageType(StatusMessageType.PENDING.getValue());
            status.setPercentage(percentage);
        }
        return status;
    }
    //clean system code
    public static void cleanSystemCode(String key) {
        calcMap.remove(key);
    }

    //clean all system code
    public static void cleanAllSystemCode() {
        calcMap.clear();
    }

    public static void main(String[] args) {
        createSystemCodeMap("PW");
        updateValue("PW", "method1", new Status(StatusMessageType.IN_PROGRESS.getValue(), 10.0));
        updateValue("PW", "method2", new Status(StatusMessageType.IN_PROGRESS.getValue(), 20.0));
        updateValue("PW", "method3", new Status(StatusMessageType.IN_PROGRESS.getValue(), 70.0));
        System.out.println(getSystemCodeMap("PW"));
        System.out.println(getPercentage("PW"));

        createSystemCodeMap("AT");
        updateValue("AT", "method1", new Status(StatusMessageType.IN_PROGRESS.getValue(), 10.0));
        updateValue("AT", "method2", new Status(StatusMessageType.IN_PROGRESS.getValue(), 60.0));
        updateValue("AT", "method3", new Status(StatusMessageType.IN_PROGRESS.getValue(), 30.0));
        System.out.println(getSystemCodeMap("AT"));
        System.out.println(getPercentage("AT"));

        cleanSystemCode("PW");
        System.out.println("After cleanup : "+getSystemCodeMap("PW"));
        System.out.println(getPercentage("PW"));

        cleanSystemCode("AT");
        System.out.println("After cleanup : "+getSystemCodeMap("AT"));
        System.out.println(getPercentage("AT"));
    }
}
