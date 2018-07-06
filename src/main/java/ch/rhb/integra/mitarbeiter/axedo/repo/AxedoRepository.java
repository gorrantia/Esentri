package ch.rhb.integra.mitarbeiter.axedo.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import ch.rhb.integra.mitarbeiter.axedo.domain.EmployeeBaseData;

@Repository
public interface AxedoRepository extends CrudRepository<EmployeeBaseData, Long>{

 
}
