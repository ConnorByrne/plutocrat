<<<<<<< HEAD
package net.plutocrat.company;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends CrudRepository<Company, Long> {
	@Query(value = "SELECT c FROM Company c WHERE c.companyName LIKE '%' || :keyword || '%'")
	public List<Company> searchByName(@Param("keyword") String keyword);
	
	@Query(value="SELECT c FROM Company c WHERE c.companyName LIKE '%' || :keyword|| '%'"+
	"OR c.tickerSymbol LIKE '%' || :keyword || '%'")
	public List<Company> search(@Param("keyword") String keyword);
}
=======
package net.plutocrat.company;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends CrudRepository<Company, Long> {
	@Query(value = "SELECT c FROM Company c WHERE c.companyName LIKE '%' || :keyword || '%'")
	public List<Company> searchByName(@Param("keyword") String keyword);
	
	@Query(value="SELECT c FROM Company c WHERE c.companyName LIKE '%' || :keyword|| '%'"+
	"OR c.tickerSymbol LIKE '%' || :keyword || '%'")
	public List<Company> search(@Param("keyword") String keyword);
}
>>>>>>> 3f1b7b7c045131098ae621106a4a497b14ee1e01
