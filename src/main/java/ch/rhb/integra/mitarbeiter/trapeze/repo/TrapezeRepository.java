package ch.rhb.integra.mitarbeiter.trapeze.repo;


import ch.rhb.integra.mitarbeiter.trapeze.domain.EmployeeImport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrapezeRepository extends CrudRepository<EmployeeImport, Long>{

}
