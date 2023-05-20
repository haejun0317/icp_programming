package com.sds.icagile.cafe.api.alarm;

import java.util.Objects;

public class Alarm {
    private String api;
    private String method;
    private Object message;

    public Alarm(String api, String method, Object message) {
        this.api = api;
        this.method = method;
        this.message = message;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarm alarm = (Alarm) o;
        return Objects.equals(api, alarm.api) &&
                Objects.equals(method, alarm.method) &&
                Objects.equals(message, alarm.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(api, method, message);
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "api='" + api + '\'' +
                ", method='" + method + '\'' +
                ", message=" + message +
                '}';
    }
}
