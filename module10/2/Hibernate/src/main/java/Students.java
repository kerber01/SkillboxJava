import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name = "Students")
public class Students {
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private int age;
  @Column(name = "registration_date")
  private LocalDateTime registrationDate;

  public Students() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
  }

  @Override
  public String toString() {
    return "Students{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", age=" + age +
        ", registrationDate=" + registrationDate +
        '}';
  }
}
