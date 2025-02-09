package com.infinity.isbbe.log.service;

import com.infinity.isbbe.log.aggregate.Log;
import com.infinity.isbbe.log.etc.LogStatus;
import com.infinity.isbbe.log.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
    @Override
    public void saveLog(Log log) {
        logRepository.save(log);
    }

    @Override
    public void saveLog(String logChanger, LogStatus logStatus, String content, String target) {
        Log log = new Log(logChanger, logStatus,  content, target);
        logRepository.save(log);
    }

    @Override
    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }
}
