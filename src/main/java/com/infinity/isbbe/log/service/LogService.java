package com.infinity.isbbe.log.service;

import com.infinity.isbbe.log.aggregate.Log;
import com.infinity.isbbe.log.etc.LogStatus;

import java.util.List;

public interface LogService {
    void saveLog(Log log);

    void saveLog(String logChanger, LogStatus logStatus, String content, String target);

    List<Log> getAllLogs();
}
