package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.State;

@Repository
public interface IStateRepository extends JpaRepository<State, Long> {
    public List<State> findByGridLocatorEntity(String gridLocatorEntity);
}
