package uz.com.everbestlab.studentsproject.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.com.everbestlab.studentsproject.exception.DataNotFoundException;
import uz.com.everbestlab.studentsproject.model.dto.request.user.StudentInfoResume;
import uz.com.everbestlab.studentsproject.model.entity.Gender;
import uz.com.everbestlab.studentsproject.model.entity.Student;
import uz.com.everbestlab.studentsproject.repository.StudentRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
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
        document.add(new Paragraph("Languages: " + studentInfoResume.getLanguages()).setItalic());
        document.add(new Paragraph("LinkedIn: " + studentInfoResume.getLinkedIn()).setItalic());
        document.add(new Paragraph("Telegram: " + studentInfoResume.getTelegramUsername()).setItalic());
        if (student.getGender().equals(Gender.MALE)){
            document.add(new Paragraph("Gender: Male").setItalic());
        }
        if (student.getGender().equals(Gender.FEMALE)){
            document.add(new Paragraph("Gender: Female").setItalic());
        }

        document.close();

        return out.toByteArray();
    }








    public byte[] generateUsersExcel(List<Student> students) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("students");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Full name");
        headerRow.createCell(1).setCellValue("Address");
        headerRow.createCell(2).setCellValue("Gender");
        headerRow.createCell(3).setCellValue("Description");
        headerRow.createCell(4).setCellValue("Date of birth");
        headerRow.createCell(5).setCellValue("University name");
        headerRow.createCell(6).setCellValue("Specialty");
        headerRow.createCell(7).setCellValue("Study start time");
        headerRow.createCell(8).setCellValue("Study end time");
        int rowNum = 1;
        for (Student student : students) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.getFullName());
            row.createCell(1).setCellValue(student.getAddress());
            row.createCell(2).setCellValue(String.valueOf(student.getGender()));
            row.createCell(3).setCellValue(student.getDescription());
            row.createCell(4).setCellValue(student.getDateOfBirth().toString());
            row.createCell(5).setCellValue(student.getUniversity().getName());
            row.createCell(6).setCellValue(student.getSpecialization().getName());
            row.createCell(7).setCellValue(student.getStudyStartDate().toString());
            row.createCell(8).setCellValue(student.getStudyEndDate().toString());
        }
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }
}
