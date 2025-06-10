package com.example.autopark.controller;

import com.example.autopark.entity.Car;
import com.example.autopark.repository.CarRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/cars")
    public String listCars(Model model,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           @RequestParam(value = "sort", defaultValue = "releaseYear") String sortField) {
        List<Car> cars;
        if (keyword != null && !keyword.trim().isEmpty()) {
            cars = carRepository.findByBrandContainingIgnoreCaseOrOwnerNameContainingIgnoreCase(keyword, keyword);
            model.addAttribute("keyword", keyword);
        } else {
            String[] sortParams = sortField.split(",");
            Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            cars = carRepository.findAll(Sort.by(direction, sortParams[0]));
        }
        model.addAttribute("cars", cars);
        model.addAttribute("sortField", sortField);
        return "car-list";
    }

    @GetMapping("/cars/new")
    public String showCreateForm(Model model) {
        model.addAttribute("car", new Car());
        return "car-form";
    }

    @PostMapping("/cars/save")
    public String saveCar(@Valid @ModelAttribute("car") Car car, BindingResult result) {
        if (result.hasErrors()) {
            return "car-form";
        }
        carRepository.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/cars/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        model.addAttribute("car", car);
        return "car-form";
    }

    @GetMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable("id") Long id) {
        carRepository.deleteById(id);
        return "redirect:/cars";
    }

    @GetMapping("/api/cars/brand-stats")
    @ResponseBody
    public List<Map<String, Object>> getCarBrandStats() {
        return carRepository.countCarsByBrand().stream()
                .map(record -> Map.of("brand", record[0], "carCount", record[1]))
                .collect(Collectors.toList());
    }
}
