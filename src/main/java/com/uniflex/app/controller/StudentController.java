package com.uniflex.app.controller;

import com.uniflex.app.entity.Grade;
import com.uniflex.app.entity.Student;
import com.uniflex.app.entity.User;
import com.uniflex.app.repository.GradeRepository;
import com.uniflex.app.repository.StudentRepository;
import com.uniflex.app.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final StudentService studentService;

    public StudentController(StudentRepository studentRepository,
                             GradeRepository gradeRepository,
                             StudentService studentService) {
        this.studentRepository = studentRepository;
        this.gradeRepository   = gradeRepository;
        this.studentService    = studentService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {

        User user = (User) auth.getPrincipal();

        Student student = studentRepository
                .findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        List<Grade> allGrades = gradeRepository.findByStudentId(student.getId());
        List<Grade> gradesS1  = gradeRepository.findByStudentIdAndTerm(student.getId(), "S1");
        List<Grade> gradesS2  = gradeRepository.findByStudentIdAndTerm(student.getId(), "S2");

        double moyenne    = studentService.calculateAverage(allGrades);
        double moyenneS1  = studentService.calculateAverage(gradesS1);
        double moyenneS2  = studentService.calculateAverage(gradesS2);
        String mention    = studentService.calculateMention(moyenne);

        model.addAttribute("student",    student);
        model.addAttribute("grades",     allGrades);
        model.addAttribute("moyenne",    moyenne);
        model.addAttribute("moyenneS1",  moyenneS1);
        model.addAttribute("moyenneS2",  moyenneS2);
        model.addAttribute("nbGradesS1", gradesS1.size());
        model.addAttribute("nbGradesS2", gradesS2.size());
        model.addAttribute("mention",    mention);

        return "student/dashboard";
    }
}