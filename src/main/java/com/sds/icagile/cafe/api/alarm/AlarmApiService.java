package com.sds.icagile.cafe.api.alarm;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="alarm", url="http://localhost:9000/api/v1")
public interface AlarmApiService {
    @PostMapping("/alarms")
    void createAlarm(Alarm alarm);
}
