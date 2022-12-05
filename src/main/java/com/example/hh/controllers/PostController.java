package com.example.hh.controllers;

import com.example.hh.models.PostModel;
import com.example.hh.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/vacancy")
public class PostController {

    private final PostRepository postRepository;
    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/{id}")
    public String getVacancyPage(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)) return "redirect:/";

        Optional<PostModel> post = postRepository.findById(id);
        ArrayList<PostModel> result = new ArrayList<>();
        post.ifPresent(result :: add);

        model.addAttribute("post", result);
        model.addAttribute("title", result.get(0).getTitle());
        return "vacancy";
    }

    //@DeleteMapping
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable(value = "id") long id){
        postRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/create")
    public String createPost(@RequestParam(value = "title") String title,
                             @RequestParam(value = "city") String city,
                             @RequestParam(value = "experience") int experience,
                             @RequestParam(value = "language") String language,
                             @RequestParam(value = "description") String description){
        PostModel postModel = new PostModel(null, title, description, language, LocalDate.now(), "Noname", city, experience);
        postRepository.save(postModel);
        return "redirect:/";
    }

    //@PutMapping
    @PostMapping("/{id}/edit")
    public String updatePost(@PathVariable(value = "id") long id,
                             @RequestParam(value = "title") String title,
                             @RequestParam(value = "city") String city,
                             @RequestParam(value = "experience") int experience,
                             @RequestParam(value = "language") String language,
                             @RequestParam(value = "description") String description){
        PostModel post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setCity(city);
        post.setDescription(description);
        post.setLanguage(language);
        post.setExperience(experience);
        postRepository.save(post);
        return "redirect:/vacancy/" + id;
    }
}
