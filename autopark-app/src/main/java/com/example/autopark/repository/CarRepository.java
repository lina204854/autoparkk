package com.example.autopark.repository;

import com.example.autopark.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    
    List<Car> findByBrandContainingIgnoreCaseOrOwnerNameContainingIgnoreCase(String brand, String ownerName);

    @Query("SELECT c.brand, COUNT(c) FROM Car c GROUP BY c.brand")
    List<Object[]> countCarsByBrand();
}
