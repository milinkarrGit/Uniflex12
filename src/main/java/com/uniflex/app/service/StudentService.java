package com.uniflex.app.service;

import com.uniflex.app.entity.Grade;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    public double calculateAverage(List<Grade> grades) {
        if (grades.isEmpty()) return 0;
        double total = 0;
        double totalCoef = 0;
        for (Grade g : grades) {
            double coef = g.getSubject().getCoefficient();
            total += g.getValue() * coef;
            totalCoef += coef;
        }
        return totalCoef == 0 ? 0 : total / totalCoef;
    }

    public String calculateMention(double moyenne) {
        if (moyenne < 10) return "ÉCHEC";
        if (moyenne < 12) return "PASSABLE";
        if (moyenne < 14) return "ASSEZ BIEN";
        if (moyenne < 16) return "BIEN";
        return "TRÈS BIEN";
    }
}