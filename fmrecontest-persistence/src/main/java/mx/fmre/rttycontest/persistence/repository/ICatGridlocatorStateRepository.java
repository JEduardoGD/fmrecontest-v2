package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import mx.fmre.rttycontest.persistence.model.CatGridlocatorState;

public interface ICatGridlocatorStateRepository extends JpaRepository<CatGridlocatorState, Integer> {
    public CatGridlocatorState findByGridlocator(@Param("gridlocator") String gridlocator);
}
