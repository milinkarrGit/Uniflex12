package com.uniflex.app.controller;

import com.uniflex.app.entity.*;
import com.uniflex.app.repository.GradeRepository;
import com.uniflex.app.repository.StudentRepository;
import com.uniflex.app.repository.TeacherRepository;
import com.uniflex.app.repository.SubjectRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    public TeacherController(TeacherRepository teacherRepository,
                             SubjectRepository subjectRepository,
                             StudentRepository studentRepository,
                             GradeRepository gradeRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.gradeRepository   = gradeRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        User user = (User) auth.getPrincipal();

        List<Subject> subjects = subjectRepository.findAll().stream()
                .filter(s -> s.getTeacher().getEmail() != null
                        && s.getTeacher().getEmail().equals(user.getUsername()))
                .toList();

        model.addAttribute("subjects", subjects);
        model.addAttribute("username", user.getUsername());
        return "teacher/dashboard";
    }

    @GetMapping("/subjects/{id}/grades")
    public String grades(@PathVariable Long id, Model model) {
        Subject subject = subjectRepository.findById(id).orElseThrow();

        List<Student> students = studentRepository.findAll().stream()
                .filter(s -> s.getClassGroup().getId().equals(subject.getClassGroup().getId()))
                .toList();

        model.addAttribute("subject",  subject);
        model.addAttribute("students", students);
        model.addAttribute("grades",   gradeRepository.findAll().stream()
                .filter(g -> g.getSubject().getId().equals(id)).toList());
        return "teacher/grades";
    }

    @PostMapping("/subjects/{id}/grades/save")
    public String saveGrade(@PathVariable Long id,
                            @RequestParam Long studentId,
                            @RequestParam double value,
                            @RequestParam String term,
                            RedirectAttributes ra) {
        Grade grade = new Grade();
        grade.setSubject(subjectRepository.findById(id).orElseThrow());
        grade.setStudent(studentRepository.findById(studentId).orElseThrow());
        grade.setValue(value);
        grade.setTerm(term);
        gradeRepository.save(grade);
        ra.addFlashAttribute("success", "Note enregistrée.");
        return "redirect:/teacher/subjects/" + id + "/grades";
    }
}