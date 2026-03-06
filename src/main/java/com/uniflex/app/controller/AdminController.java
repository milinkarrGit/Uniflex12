package com.uniflex.app.controller;

import com.uniflex.app.entity.*;
import com.uniflex.app.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final TeacherRepository    teacherRepository;
    private final StudentRepository    studentRepository;
    private final SubjectRepository    subjectRepository;
    private final ClassGroupRepository classGroupRepository;
    private final UserRepository       userRepository;
    private final GradeRepository      gradeRepository;
    private final PasswordEncoder      passwordEncoder;

    public AdminController(TeacherRepository teacherRepository,
                           StudentRepository studentRepository,
                           SubjectRepository subjectRepository,
                           ClassGroupRepository classGroupRepository,
                           UserRepository userRepository,
                           GradeRepository gradeRepository,
                           PasswordEncoder passwordEncoder) {
        this.teacherRepository    = teacherRepository;
        this.studentRepository    = studentRepository;
        this.subjectRepository    = subjectRepository;
        this.classGroupRepository = classGroupRepository;
        this.userRepository       = userRepository;
        this.gradeRepository      = gradeRepository;
        this.passwordEncoder      = passwordEncoder;
    }

    // DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("nbTeachers", teacherRepository.count());
        model.addAttribute("nbStudents", studentRepository.count());
        model.addAttribute("nbSubjects", subjectRepository.count());
        model.addAttribute("nbClasses",  classGroupRepository.count());
        model.addAttribute("nbGrades",   gradeRepository.count());
        model.addAttribute("recentGrades", gradeRepository.findAll()
                .stream().limit(10).toList());
        return "admin/dashboard";
    }

    // PROFESSEURS
    @GetMapping("/teachers")
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherRepository.findAll());
        model.addAttribute("teacher",  new Teacher());
        return "admin/teachers";
    }

    @PostMapping("/teachers/save")
    public String saveTeacher(@ModelAttribute Teacher teacher, RedirectAttributes ra) {
        teacher.setActive(true);
        teacherRepository.save(teacher);
        ra.addFlashAttribute("success", "Professeur enregistré.");
        return "redirect:/admin/teachers";
    }

    @GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable Long id, RedirectAttributes ra) {
        teacherRepository.deleteById(id);
        ra.addFlashAttribute("success", "Professeur supprimé.");
        return "redirect:/admin/teachers";
    }

    // CLASSES
    @GetMapping("/classes")
    public String listClasses(Model model) {
        model.addAttribute("classes",    classGroupRepository.findAll());
        model.addAttribute("classGroup", new ClassGroup());
        return "admin/classes";
    }

    @PostMapping("/classes/save")
    public String saveClass(@ModelAttribute ClassGroup classGroup, RedirectAttributes ra) {
        classGroup.setActive(true);
        classGroupRepository.save(classGroup);
        ra.addFlashAttribute("success", "Classe enregistrée.");
        return "redirect:/admin/classes";
    }

    @GetMapping("/classes/delete/{id}")
    public String deleteClass(@PathVariable Long id, RedirectAttributes ra) {
        classGroupRepository.deleteById(id);
        ra.addFlashAttribute("success", "Classe supprimée.");
        return "redirect:/admin/classes";
    }

    // MATIERES
    @GetMapping("/subjects")
    public String listSubjects(Model model) {
        model.addAttribute("subjects",  subjectRepository.findAll());
        model.addAttribute("subject",   new Subject());
        model.addAttribute("teachers",  teacherRepository.findByActiveTrue());
        model.addAttribute("classes",   classGroupRepository.findByActiveTrue());
        return "admin/subjects";
    }

    @PostMapping("/subjects/save")
    public String saveSubject(@ModelAttribute Subject subject,
                              @RequestParam Long teacherId,
                              @RequestParam Long classGroupId,
                              RedirectAttributes ra) {
        subject.setTeacher(teacherRepository.findById(teacherId).orElseThrow());
        subject.setClassGroup(classGroupRepository.findById(classGroupId).orElseThrow());
        subject.setActive(true);
        subjectRepository.save(subject);
        ra.addFlashAttribute("success", "Matière enregistrée.");
        return "redirect:/admin/subjects";
    }

    @GetMapping("/subjects/delete/{id}")
    public String deleteSubject(@PathVariable Long id, RedirectAttributes ra) {
        subjectRepository.deleteById(id);
        ra.addFlashAttribute("success", "Matière supprimée.");
        return "redirect:/admin/subjects";
    }

    // ETUDIANTS
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("student",  new Student());
        model.addAttribute("classes",  classGroupRepository.findByActiveTrue());
        return "admin/students";
    }

    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute Student student,
                              @RequestParam Long classGroupId,
                              @RequestParam String username,
                              @RequestParam String password,
                              RedirectAttributes ra) {
        student.setClassGroup(classGroupRepository.findById(classGroupId).orElseThrow());
        student.setActive(true);

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_STUDENT");
        userRepository.save(user);

        student.setUser(user);
        studentRepository.save(student);

        ra.addFlashAttribute("success", "Étudiant inscrit.");
        return "redirect:/admin/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes ra) {
        studentRepository.deleteById(id);
        ra.addFlashAttribute("success", "Étudiant supprimé.");
        return "redirect:/admin/students";
    }

    // NOTES
    @GetMapping("/grades")
    public String listGrades(Model model) {
        model.addAttribute("grades",   gradeRepository.findAll());
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "admin/grades";
    }

    @PostMapping("/grades/save")
    public String saveGrade(@RequestParam Long studentId,
                            @RequestParam Long subjectId,
                            @RequestParam double value,
                            @RequestParam String term,
                            RedirectAttributes ra) {
        Grade grade = new Grade();
        grade.setStudent(studentRepository.findById(studentId).orElseThrow());
        grade.setSubject(subjectRepository.findById(subjectId).orElseThrow());
        grade.setValue(value);
        grade.setTerm(term);
        gradeRepository.save(grade);
        ra.addFlashAttribute("success", "Note enregistrée.");
        return "redirect:/admin/grades";
    }

    @GetMapping("/grades/delete/{id}")
    public String deleteGrade(@PathVariable Long id, RedirectAttributes ra) {
        gradeRepository.deleteById(id);
        ra.addFlashAttribute("success", "Note supprimée.");
        return "redirect:/admin/grades";
    }
}