package com.mattrack.sport;

import com.mattrack.technique.TechniqueCategory;
import com.mattrack.training.TrainingType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sports")
public class SportController {

    @GetMapping
    public List<SportType> findAllSports() {
        return Arrays.asList(SportType.values());
    }

    @GetMapping("/training-types")
    public Map<SportType, List<TrainingType>> findTrainingTypesBySport() {
        return Arrays.stream(TrainingType.values())
                .collect(Collectors.groupingBy(TrainingType::getSportType));
    }

    @GetMapping("/technique-categories")
    public List<TechniqueCategory> findTechniqueCategories() {
        return Arrays.asList(TechniqueCategory.values());
    }
}
