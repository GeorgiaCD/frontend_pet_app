package com.bnta.backend_project_group4.services;

import com.bnta.backend_project_group4.models.Food;
import com.bnta.backend_project_group4.models.FoodDTO;
import com.bnta.backend_project_group4.models.Pet;
import com.bnta.backend_project_group4.repositories.FoodRepository;
import com.bnta.backend_project_group4.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    PetRepository petRepository;

    public List<Food> getAllFoods(){
        return foodRepository.findAll();
    }

    public Optional<Food> getFoodById(Long id){
        return foodRepository.findById(id);
    }

    public Food saveFood(FoodDTO foodDTO){
        Food food = new Food(foodDTO.getName(), foodDTO.getNutritionValue());
        
        foodRepository.save(food);
        return food;
    }

    public void deleteFood(Long id){
        Food foodToDelete = foodRepository.findById(id).get();

        for(Pet pet : foodToDelete.getPets()){
            pet.removeFood(foodToDelete);
            petRepository.save(pet);
        }
        foodRepository.deleteById(id);
    }

    public Food updateFood(FoodDTO foodDTO, Long id){
        Food foodToUpdate = foodRepository.findById(id).get();

        foodToUpdate.setName(foodDTO.getName());
        foodToUpdate.setNutritionValue(foodDTO.getNutritionValue());

        foodRepository.save(foodToUpdate);
        return foodToUpdate;
    }
}
