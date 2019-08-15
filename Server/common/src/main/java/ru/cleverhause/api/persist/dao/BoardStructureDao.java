package ru.cleverhause.api.persist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cleverhause.api.persist.entities.BoardStructure;

/**
 * Created by
 *
 * @author Aleksandr_Ivanov1
 * @date 6/10/2018.
 */
public interface BoardStructureDao extends JpaRepository<BoardStructure, Long> {
}
