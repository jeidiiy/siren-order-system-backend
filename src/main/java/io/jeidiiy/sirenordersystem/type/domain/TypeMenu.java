package io.jeidiiy.sirenordersystem.type.domain;

import io.jeidiiy.sirenordersystem.menu.domain.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@Table(name = "type_menus")
@Entity
public class TypeMenu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer typeMenuId;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Type type;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Menu menu;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    TypeMenu typeMenu = (TypeMenu) o;

    if (typeMenu.getTypeMenuId() != null) {
      return Objects.equals(getTypeMenuId(), typeMenu.getTypeMenuId());
    }

    return Objects.equals(getType(), typeMenu.getType())
        && Objects.equals(getMenu(), typeMenu.getMenu());
  }

  @Override
  public int hashCode() {
    if (getTypeMenuId() != null) {
      return Objects.hash(getTypeMenuId());
    }

    return Objects.hash(getType(), getMenu());
  }
}
