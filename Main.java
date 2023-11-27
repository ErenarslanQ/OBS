import java.util.*;
class Course {
     String lessoncode;
    String schedule;
    String name;
    int studentCount;

    public Course(String lessoncode, String name, String schedule) {
        this.lessoncode = lessoncode;
        this.name = name;
        this.schedule = schedule;
        this.studentCount = 0;
    }

    public boolean canAddStudent() {
        return studentCount < 5;
    }

    public void addStudent() {
        studentCount++;
    }

    public void dropStudent() {
        if (studentCount > 0) {
            studentCount--;
        }
    }

    public String toString() {
        return name + " (" + lessoncode + ") - Schedule: " + schedule + " - Students: " + studentCount + "/5";
    }

    public boolean hasConflict(String otherSchedule) {
        return this.schedule.equals(otherSchedule);
    }
}

class Student {
    long studentNumber;
    String name;
    List<Course> courses;

    public Student(long studentNumber, String name) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public boolean canAddCourse() {
        return courses.size() < 5;
    }

    public boolean hasScheduleConflict(String schedule) {
        for (Course enrolledCourse : courses) {
            if (enrolledCourse.hasConflict(schedule)) {
                return false;
            }
        }
        return true;
    }

    public void addCourse(Course course) {
        if (canAddCourse()) {
            for (Course registeredCourse : courses) {
                if (registeredCourse.hasConflict(course.schedule)) {
                    System.out.println("Cannot add " + course.name + " for " + name + " due to schedule conflict with " + registeredCourse.name);
                    return;
                }
            }

            courses.add(course);
            course.addStudent();
            System.out.println(name + " has successfully added " + course.name);
        } else {
            System.out.println("Cannot add " + course.name + " for " + name + ". Student already registered for 5 courses.");
        }
    }

    public void dropCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
            course.dropStudent();
            System.out.println(name + " has successfully dropped " + course.name);
        } else {
            System.out.println(name + " did not select " + course.name + " earlier.");
        }
    }

    public String toString() {
        return name + " (" + studentNumber + ") - Courses: " + courses.size() + "/5";
    }
}

public class Main {
    public static void main(String[] args) {
        Map<String, Course> courses = initializeCourses();
        Map<Long, Student> students = initializeStudents();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Show Course List");
            System.out.println("2. Show Student List");
            System.out.println("3. Add Course to a Student");
            System.out.println("4. Drop Course from a Student");
            System.out.println("5. Exit");
            System.out.print("Please Choose An Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showCourses(courses);
                    break;
                case 2:
                    showStudents(students);
                    break;
                case 3:
                    addCourseToStudent(scanner, students, courses);
                    break;
                case 4:
                    dropCourseFromStudent(scanner, students);
                    break;
                case 5:
                    System.out.println("Exiting the program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.Please enter a valid option.");
            }
        }
    }

    private static Map<String, Course> initializeCourses() {
        Map<String, Course> courses = new HashMap<>();
        courses.put("ACM212", new Course("ACM212", "ileri_veritabani_uygulamalari", "Monday, 09:00-12:00"));
        courses.put("ACM465", new Course("ACM465", "Yapay_Zeka", "Monday, 13:00-16:00"));
        courses.put("ACM365", new Course("ACM365", "İleri_Web_Tasarimi", "Tuesday, 13:00-16:00"));
        courses.put("ACM321", new Course("ACM321", "Nesne_Yönelimli_Programlama", "Tuesday, 16:00-19:00"));
        courses.put("ACM369", new Course("ACM369", "İşletim_Sistemleri", "Wednesday, 09:00-12:00"));
        courses.put("AFN360", new Course("AFN360", "Blockchain_Uygulamaları", "Wednesday, 13:00-16:00"));
        courses.put("ACM437", new Course("ACM437", "Nesnelerin_İnterneti", "Wednesday, 13:00-16:00"));
        courses.put("HTR301", new Course("HTR301", "Atatürk_İlkeleri", "Friday, 16:00-18:00"));
        courses.put("ACM105", new Course("ACM105", "Enformatik_Yönetimi", "Friday, 09:00-12:00"));
        courses.put("STAT411", new Course("STAT411", "İleri_İstatistik_Uygulamaları", "Thursday, 16:00-19:00"));
        return courses;
    }

    private static Map<Long, Student> initializeStudents() {
        Map<Long, Student> students = new HashMap<>();
        students.put(20191308041L, new Student(20191308041L, "Eren"));
        students.put(20191308042L, new Student(20191308042L, "Eren1"));
        students.put(20191308043L, new Student(20191308043L, "Eren2"));
        students.put(20191308044L, new Student(20191308044L, "Eren3"));
        students.put(20191308045L, new Student(20191308045L, "Eren4"));
        students.put(20191308046L, new Student(20191308046L, "Eren5"));
        students.put(20191308047L, new Student(20191308047L, "Eren6"));
        students.put(20191308048L, new Student(20191308048L, "Eren7"));
        students.put(20191308049L, new Student(20191308049L, "Eren8"));
        students.put(20191308050L, new Student(20191308050L, "Eren9"));
        return students;
    }

    private static void showCourses(Map<String, Course> courses) {
        for (Course course : courses.values()) {
            System.out.println(course);
        }
    }
    private static void showStudents(Map<Long, Student> students) {
        for (Student student : students.values()) {
            System.out.println(student);
        }
    }
    private static void addCourseToStudent(Scanner scanner, Map<Long, Student> students, Map<String, Course> courses) {
        System.out.print("Enter student number: ");
        long studentNumber = scanner.nextLong();
        scanner.nextLine();
        if (students.containsKey(studentNumber)) {
            Student student = students.get(studentNumber);
            System.out.print("Enter course code: ");
            String courseCode = scanner.nextLine();
            Course course = courses.get(courseCode);
            if (course != null) {
                if (course.canAddStudent() && student.canAddCourse() && student.hasScheduleConflict(course.schedule)) {
                    student.addCourse(course);
                    System.out.println(student.name + " has successfully added " + course.name);
                } else {
                    System.out.println("Cannot add " + course.name + " for " + student.name +
                            ". Either the course is full or there is a schedule conflict.");
                }
            } else {
                System.out.println("Invalid course code.");
            }
        } else {
            System.out.println("Invalid student number.");
        }
    }
    private static void dropCourseFromStudent(Scanner scanner, Map<Long, Student> students) {
        System.out.print("Enter student number: ");
        long studentNumber = scanner.nextLong();
        scanner.nextLine();

        if (students.containsKey(studentNumber)) {
            Student student = students.get(studentNumber);
            if (!student.courses.isEmpty()) {
                System.out.println("Courses enrolled by " + student.name + ":");
                for (int i = 0; i < student.courses.size(); i++) {
                    System.out.println((i + 1) + ". " + student.courses.get(i).name);
                }
                System.out.print("Enter the number of the course to drop: ");
                int courseNumber;
                try {
                    courseNumber = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine();
                    return;
                }
                if (courseNumber >= 1 && courseNumber <= student.courses.size()) {
                    Course courseToDrop = student.courses.get(courseNumber - 1);
                    student.dropCourse(courseToDrop);
                    System.out.println(student.name + " has successfully dropped " + courseToDrop.name);
                } else {
                    System.out.println("Invalid course number.");
                }
            } else {
                System.out.println(student.name + " is not enrolled in any courses.");
            }
        } else {
            System.out.println("Invalid student number.");
        }
    }
}
