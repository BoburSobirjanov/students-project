package uz.com.everbestlab.studentsproject.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Image;
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
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Paragraph infoParagraph = new Paragraph("RESUME")
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18)
                .setBorder(new SolidBorder(1));

        document.add(infoParagraph);
        if (student.getPicturePath() != null){
            Path picturePath = Paths.get(student.getPicturePath());
        try {
            Image img = new Image(ImageDataFactory.create(picturePath.toAbsolutePath().toString()));
            img.setWidth(100);
            img.setHeight(100);
            document.add(img);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add picture to the PDF", e);
            }
        }
        document.add(new Paragraph("Full name: "+student.getFullName()).setBold());
        document.add(new Paragraph("📞Phone number: " + studentInfoResume.getPhoneNumber()));
        document.add(new Paragraph("🗓️Date of birth: " + student.getDateOfBirth()));
        document.add(new Paragraph("📧Email: " + studentInfoResume.getEmail()));
        document.add(new Paragraph("🏠Address: " + student.getAddress()));
        document.add(new Paragraph("🖥️Major: " + studentInfoResume.getMajor()));
        document.add(new Paragraph("⭐Skills: " + studentInfoResume.getSkills()));
        document.add(new Paragraph("⌛Experience: " + studentInfoResume.getYearsOfExperience()));
        document.add(new Paragraph("🏢Worked Places: " + studentInfoResume.getWorkedPlaces()));
        document.add(new Paragraph("🏆Achievement: " + studentInfoResume.getAchievements()));
        document.add(new Paragraph("📜Job type: " + studentInfoResume.getJobType()));
        document.add(new Paragraph("🏫University: " + student.getUniversity().getName()));
        document.add(new Paragraph("⏲️Study start: " + student.getStudyStartDate()));
        document.add(new Paragraph("⏲️Study end: " + student.getStudyEndDate()));
        document.add(new Paragraph("📚Languages: " + studentInfoResume.getLanguages()));
        document.add(new Paragraph("🔗LinkedIn: " + studentInfoResume.getLinkedIn()));
        document.add(new Paragraph("🔗Telegram: " + studentInfoResume.getTelegramUsername()));
        if (student.getGender().equals(Gender.MALE)){
            document.add(new Paragraph("Gender: Male"));
        }
        if (student.getGender().equals(Gender.FEMALE)){
            document.add(new Paragraph("Gender: Female"));
        }

        PdfCanvas canvas = new PdfCanvas(pdf.getFirstPage());
        float leftMargin = 28.35f;
        float rightMargin = 28.35f;
        float topMargin = 28.35f;
        float bottomMargin = 28.35f;
        float x = leftMargin;
        float y = bottomMargin;
        float width = PageSize.A4.getWidth() - leftMargin - rightMargin;
        float height = PageSize.A4.getHeight() - topMargin - bottomMargin;

        canvas.setLineWidth(2);
        canvas.rectangle(x, y, width, height);
        canvas.stroke();
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
