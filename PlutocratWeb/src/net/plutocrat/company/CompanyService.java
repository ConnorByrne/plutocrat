package net.plutocrat.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
	@Autowired
	private CompanyRepository repo;
	
	public List<Company> listAll(){
		return (List<Company>) repo.findAll();
	}
	
	public List<Company> findByName(String keyword) {
		return repo.searchByName(keyword);
	}
	
	public List<Company> search(String keyword){
		return repo.search(keyword);
	}
}
