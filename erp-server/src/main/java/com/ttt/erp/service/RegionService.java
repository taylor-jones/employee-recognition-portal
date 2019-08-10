package com.ttt.erp.service;

import com.ttt.erp.model.Award;
import com.ttt.erp.model.Employee;
import com.ttt.erp.model.EmployeeRegion;
import com.ttt.erp.model.Region;
import com.ttt.erp.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegionService extends LogService {

    @Autowired
    private RegionRepository repository;

    // create new region
    public Optional<Region> createRegion(Long userAccountId, Region region) {
        try {
            Region newRegion = repository.save(region);
            logInsert(userAccountId, newRegion.getClass().getSimpleName(), newRegion.getId());
            return Optional.ofNullable(newRegion);
        } catch (Exception e) {
            System.out.println("Error on Region insert");
            e.printStackTrace();
            return Optional.empty();
        }
    }


    // update existing region
    public Optional<Region> updateRegion(Long userAccountId, Long regionId, Region modified) {
        try {
            Region existing = this.repository.findById(regionId);
            modified.setId(regionId);
            logUpdate(userAccountId, existing.getClass().getSimpleName(), existing.getId(), existing, modified);
            return Optional.ofNullable(this.repository.save(modified));
        } catch (Exception e) {
            System.out.println("Error on Region update");
            e.printStackTrace();
            return Optional.empty();
        }
    }


    // delete region
    public ResponseEntity<String> deleteRegion(Long userAccountId, Long regionId) {
        try {
            Region toDelete = this.repository.findById(regionId);
            logDelete(userAccountId, toDelete.getClass().getSimpleName(), regionId);
            this.repository.delete(toDelete);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error on Region update");
            e.printStackTrace();
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Get all the employees associated with the region.
     * @param region - the region to get all employees for
     * @return an object of employees
     */
    public Set<Employee> findEmployees(Region region) {
        Set<Employee> employees = new HashSet<>();
        Set<EmployeeRegion> employeeRegions = region.getEmployees();

        employeeRegions.forEach(er -> {
            employees.add(er.getEmployee());
        });

        return employees;
    };


    /**
     * Get all awards given to employees associated with the region.
     * @param region - the region to get all employee awards for
     * @return an object of awards
     */
    public List<Award> findAwards(Region region) {
        List<Award> awards = new ArrayList<>();
        Set<Employee> employees = this.findEmployees(region);

        employees.forEach(employee -> {
            List<Award> employeeAwards = employee.getAwards();
            awards.addAll(employeeAwards);
        });

        return awards;
    }


    public List<Object[]> getAwardCountsForRegions() {
        return this.repository.getAwardCountsForRegions();
    }

}
