package io.jeidiiy.sirenordersystem.product.domain;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 같은 카테고리에 같은 제목이 여러 개 들어가지 않도록 제목과 카테고리를 묶어서 유니크 키 제약을 둠
 * 이에 따라 인덱스도 만들어 줌
 *
 * @author jeidiiy
 */
@Getter
@NoArgsConstructor
@ToString
@Table(
    name = "types",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "UK_type_title_category",
          columnNames = {"title", "category"})
    },
    indexes = {@Index(name = "IDX_type_title_category", columnList = "title, category")})
@Entity
public class Type {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column private String title;

  @Column private String description;

  @Enumerated(EnumType.STRING)
  @Column
  private Category category;

  private Type(String title, String description, Category category) {
    this.title = title;
    this.description = description;
    this.category = category;
  }

  public static Type of(String title, String description, Category category) {
    return new Type(title, description, category);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Type type = (Type) o;

    if (type.getId() != null) {
      return Objects.equals(getId(), type.getId());
    }

    return Objects.equals(getTitle(), type.getTitle())
        && Objects.equals(getDescription(), type.getDescription())
        && getCategory() == type.getCategory();
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return Objects.hash(getId());
    }

    return Objects.hash(getTitle(), getDescription(), getCategory());
  }
}
