package guru.springframework.services;

import guru.springframework.entities.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    @Override
    public void saveImage(Long recipeId, MultipartFile multipartFile) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if(!optionalRecipe.isPresent()) {
            throw new RuntimeException("Recipe with id " + recipeId + " was not found");
        }

        Recipe recipe = optionalRecipe.get();

        try {
            Byte[] image = new Byte[multipartFile.getBytes().length];

            int i = 0;
            for(byte b : multipartFile.getBytes()) {
                image[i] = b;
                i++;
            }

            recipe.setImage(image);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error occured " + e);
            throw new RuntimeException(e);
        }

    }
}
