package com.erling.controllerJ.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/example")
public class GetCalculator {

    private GetCalculatorData getCalculatorData;

    @Autowired
    public void setGetCalculatorData(GetCalculatorData getCalculatorData) {
        this.getCalculatorData = getCalculatorData;
    }

    @GetMapping("/calculate")
    public String Test(@RequestParam String test1){
        System.out.println(test1);
        return "Hello World Calculator";
    }

    @GetMapping("/calculate2")
    public Map<String, Double> getData(
        @RequestParam    double LoanAmount, // 贷款金额
        @RequestParam    int AgeLimit, // 年限,
        @RequestParam    double InterestRate // 利率
    ){
        return getCalculatorData.getCalculatorData(LoanAmount, AgeLimit, InterestRate/100); // 利率转换为小数
    }


}
