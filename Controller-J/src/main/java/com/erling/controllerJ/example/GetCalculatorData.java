package com.erling.controllerJ.example;

import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;


public class GetCalculatorData {


    public Map<String, Double> getCalculatorData(
            double LoanAmount, // 贷款金额
            int AgeLimit, // 年限,
            double InterestRate // 利率
            ) {
        Map<String, Double> mapData = new HashMap<>();
        double MonthlyRepayments = LoanAmount * InterestRate / 12;
        double AllRepayments = MonthlyRepayments * AgeLimit * 12+LoanAmount;

        mapData.put("每月还款:",  MonthlyRepayments);
        mapData.put("总还款:",  AllRepayments);
        return mapData;
    }
}
