package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeFinder;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private RecipeFinder recipeFinder;
    private ImageService imageService;

    public ImageController(RecipeFinder recipeFinder, ImageService imageService) {
        this.recipeFinder = recipeFinder;
        this.imageService = imageService;
    }
    @GetMapping("/recipe/{recipeId}/image")
    public String getImageForm(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeFinder.findRecipeById(Long.valueOf(recipeId)));
        return "/recipe/imageuploadform";
    }
    @PostMapping("/recipe/{recipeId}/image")
    public String uploadImage(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile multipartFile) {
        imageService.saveImage(Long.valueOf(recipeId), multipartFile);
        return "redirect:/recipe/show/" + recipeId;
    }
    @GetMapping("/recipe/{recipeId}/recipeimage")
    public void getImageFromDb(@PathVariable String recipeId, HttpServletResponse httpServletResponse) throws IOException {
        RecipeCommand recipe = recipeFinder.findCommandById(Long.valueOf(recipeId));

        byte[] image = new byte[recipe.getImage().length];

        int i = 0;
        for(Byte b : recipe.getImage()) {
            image[i++] = b;
        }

        httpServletResponse.setContentType("image/png");
        InputStream inputStream = new ByteArrayInputStream(image);

        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
    }
}
