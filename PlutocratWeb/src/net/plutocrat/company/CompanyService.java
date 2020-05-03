<<<<<<< HEAD
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
=======
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
>>>>>>> 3f1b7b7c045131098ae621106a4a497b14ee1e01
