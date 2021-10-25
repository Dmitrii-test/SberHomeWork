package ru.dmitrii.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dmitrii.mvc.model.CalculatorImpl;
import ru.dmitrii.mvc.model.Factorial;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.WeakHashMap;

@Controller
public class MainController {
    WeakHashMap<Integer, Factorial> hashMap = new WeakHashMap();


    @GetMapping("/")
    public String sayHello( @RequestParam (value = "num", required = false) Integer num,
                           Model model) {
        if (num== null || num==0) num = 1;
        Factorial factorial;
        if (hashMap.containsKey(num)) {
            factorial = hashMap.get(num);
        } else {
            factorial = new Factorial(CalculatorImpl.factorial(num));
            hashMap.put(num, factorial);
        }
        model.addAttribute("num", num);
        model.addAttribute("result", factorial.getResult());
        model.addAttribute("time", factorial.getDateTime()
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM)));
        return "calc";
    }
}
