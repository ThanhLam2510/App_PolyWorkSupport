package com.example.app_supportpolywork.data.model.support_model;

public enum Salary implements FilterField{
    ALL("Tất cả"), SELECT1("Dưới 3 triệu"), SELECT2("3 - 5 triệu"), SELECT3("5 - 7 triệu"), SELECT4("7 - 10 triệu"), SELECT5("10 - 12 triệu"), SELECT6("12 - 15 triệu"), SELECT7("15 - 20 triệu"), SELECT8("20 - 25 triệu"), SELECT9("25 - 30 triệu"), SELECT10("Trên 30 triệu");
    public String value;

    Salary(String s) {
        value = s;
    }

    @Override
    public String getValue() {
        return value;
    }
}