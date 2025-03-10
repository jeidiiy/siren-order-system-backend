package io.jeidiiy.sirenordersystem.menu.domain;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "menus")
@Entity
public class Menu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer menuId;

  @Column(length = 50, nullable = false)
  private String krName;

  @Column(length = 50, nullable = false)
  private String enName;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private Integer price;

  @Column private Category category;

  @Column(length = 2048)
  private String imageUrl;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Menu menu = (Menu) o;

    if (menu.getMenuId() != null) {
      return Objects.equals(getMenuId(), menu.getMenuId());
    }

    return Objects.equals(getKrName(), menu.getKrName())
        && Objects.equals(getEnName(), menu.getEnName())
        && Objects.equals(getDescription(), menu.getDescription())
        && Objects.equals(getPrice(), menu.getPrice())
        && getCategory() == menu.getCategory()
        && Objects.equals(getImageUrl(), menu.getImageUrl());
  }

  @Override
  public int hashCode() {
    if (getMenuId() != null) {
      return Objects.hash(getMenuId());
    }

    return Objects.hash(
        getKrName(), getEnName(), getDescription(), getPrice(), getCategory(), getImageUrl());
  }
}
