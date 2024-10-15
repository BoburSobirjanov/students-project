package uz.com.everbestlab.studentsproject.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.com.everbestlab.studentsproject.exception.DataNotFoundException;
import uz.com.everbestlab.studentsproject.model.dto.request.user.StudentInfoResume;
import uz.com.everbestlab.studentsproject.model.entity.Gender;
import uz.com.everbestlab.studentsproject.model.entity.Student;
import uz.com.everbestlab.studentsproject.repository.StudentRepository;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final StudentRepository studentRepository;



    public byte[] createResume(UUID id, StudentInfoResume studentInfoResume) {
        Student student = studentRepository.getStudentById(id);
        if (student==null){
            throw new DataNotFoundException("User not found!");
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph(student.getFullName()).setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18)
                .setBold());
        document.add(new Paragraph("Phone number: " + studentInfoResume.getPhoneNumber()).setItalic());
        document.add(new Paragraph("Date of birth: " + student.getDateOfBirth()).setItalic());
        document.add(new Paragraph("Email: " + studentInfoResume.getEmail()).setItalic());
        document.add(new Paragraph("Address: " + student.getAddress()).setItalic());
        document.add(new Paragraph("Major: " + studentInfoResume.getMajor()).setItalic());
        document.add(new Paragraph("Skills: " + studentInfoResume.getSkills()).setItalic());
        document.add(new Paragraph("Experience: " + studentInfoResume.getYearsOfExperience()).setItalic());
        document.add(new Paragraph("Worked Places: " + studentInfoResume.getWorkedPlaces()).setItalic());
        document.add(new Paragraph("Achievement: " + studentInfoResume.getAchievements()).setItalic());
        document.add(new Paragraph("Job type: " + studentInfoResume.getJobType()).setItalic());
        document.add(new Paragraph("University: " + student.getUniversity().getName()).setItalic());
        document.add(new Paragraph("Study start: " + student.getStudyStartDate()).setItalic());
        document.add(new Paragraph("Study end: " + student.getStudyEndDate()).setItalic());
        if (student.getGender().equals(Gender.MALE)){
            document.add(new Paragraph("Gender: Male").setItalic());
        }
        if (student.getGender().equals(Gender.FEMALE)){
            document.add(new Paragraph("Gender: Female").setItalic());
        }

        document.close();

        return out.toByteArray();
    }
}
