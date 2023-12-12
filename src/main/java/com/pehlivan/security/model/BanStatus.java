package com.pehlivan.security.model;

import lombok.Getter;

@Getter
public enum BanStatus{
    NO("NO"),
    YES("YES");
    String status;

    BanStatus(String status){
        this.status = status;
    }
    public static BanStatus getStatus(String value) {
        value = value.trim().toLowerCase();
        return switch (value) {
            case "no" -> BanStatus.NO;
            case "yes"-> BanStatus.YES;
            default -> null;
        };
    }
}