package tr.org.lkd.lyk2015.camp.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.org.lkd.lyk2015.camp.model.Application;
import tr.org.lkd.lyk2015.camp.model.Course;
import tr.org.lkd.lyk2015.camp.model.Student;
import tr.org.lkd.lyk2015.camp.model.dto.ApplicationFormDto;
import tr.org.lkd.lyk2015.camp.repository.CourseDao;
import tr.org.lkd.lyk2015.camp.repository.StudentDao;
import tr.org.lkd.lyk2015.camp.repository.applicationDao;

@Transactional
@Service
public class ApplicationService extends GenericService<Application> {

	public enum ValidationResult {
		NO_SUCH_APPLICATION, ALREADY_VALIDATED, SUCCESS
	}

	private static final String URL_BASE = "http://localhost:8080/camp/applications/validate/";

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private applicationDao applicationDao;

	@Autowired
	private EmailService emailService;

	public void create(ApplicationFormDto applicationFormDto) {

		Application application = applicationFormDto.getApplication();
		Student student = applicationFormDto.getStudent();
		List<Long> courseIds = applicationFormDto.getPreferredCourseIds();

		// Generate email verification url
		String uuid = UUID.randomUUID().toString();
		String url = URL_BASE + uuid;

		this.emailService.sendEmail(student.getEmail(), "Basvuru onayi", url);

		application.setValidationId(uuid);

		// Add preferred courses to application entity
		List<Course> courses = this.courseDao.getByIds(courseIds);
		application.getPreferredCourses().clear();
		application.getPreferredCourses().addAll(courses);

		// Check if user exists
		Student studentFromDb = this.studentDao.getUserByTckn(student.getTckn());
		if (studentFromDb == null) {
			this.studentDao.create(student);
			studentFromDb = student;
		}

		// Set application's user
		application.setOwner(studentFromDb);

		this.applicationDao.create(application);

	}

	public ValidationResult validate(String uuid) {
		Application application = this.applicationDao.getByValidationId(uuid);

		if (application == null) {
			return ValidationResult.NO_SUCH_APPLICATION;
		}

		if (application.getValidated()) {
			return ValidationResult.ALREADY_VALIDATED;
		}

		application.setValidated(true);

		return ValidationResult.SUCCESS;
	}

}
