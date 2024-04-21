package com.example.moked_report;

public class Report {
    String date;
    String workerName;
    String machinName;
    String status;
    String reason;

    public Report(String workerName, String machinName, String status, String reason) {
        this.workerName = workerName;
        this.machinName = machinName;
        this.date = worker.getCurrentDate();
        this.status = status;
        this.reason = reason;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getMachinName() {
        return machinName;
    }

    public void setMachinName(String machinName) {
        this.machinName = machinName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
