package com.example.app_supportpolywork.data.model.support_model;

public enum ExperienceFilter implements FilterField{
    ALL("Tất cả"), NO_EXP("Chưa có kinh nghiệm"), LESS_1("Dưới 1 năm"), Y_1("1 năm"), Y_2("2 năm"), Y_3("3 năm"), Y_4("4 năm"), Y_5("5 năm"), OVER_5("trên 5 năm");
    public String value;

    ExperienceFilter(String s) {
        value = s;
    }

    @Override
    public String getValue() {
        return value;
    }
}