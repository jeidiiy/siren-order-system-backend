package io.jeidiiy.sirenordersystem.menu.repository;

import io.jeidiiy.sirenordersystem.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuJpaRepository extends JpaRepository<Menu, Integer> {}
