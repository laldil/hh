package com.example.hh.controllers;

import com.example.hh.models.PostModel;
import com.example.hh.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {

    private final PostRepository postRepository;
    @Autowired
    public MainController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    public String getMainPage(Model model){
        Iterable<PostModel> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("title", "Home");
        return "main";
    }

    @GetMapping("/vacancy/create")
    public String getCreatePage(Model model){
        model.addAttribute("title", "Create");
        return "createPost";
    }

    @GetMapping("vacancy/{id}/edit")
    public String getEditPage(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)) return "redirect:/";

        Optional<PostModel> post = postRepository.findById(id);
        ArrayList<PostModel> result = new ArrayList<>();
        post.ifPresent(result :: add);

        model.addAttribute("title", "Edit");
        model.addAttribute("post", result);
        return "editPost";
    }
}
