package org.example.todolist.controller;

import org.example.todolist.controller.form.NoteForm;
import org.example.todolist.entity.Note;
import org.example.todolist.mapper.NoteMapper;
import org.example.todolist.service.CategoryService;
import org.example.todolist.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("api/V1/notes")
public class NoteController {

    private final NoteService noteService;
    private final NoteMapper noteMapper;
    private final CategoryService categoryService;

    public NoteController(NoteService noteService, NoteMapper noteMapper, CategoryService categoryService) {
        this.noteService = noteService;
        this.noteMapper = noteMapper;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String getNotes(Model model) {
        model.addAttribute("notes", noteService.listAll());
        model.addAttribute("categories", categoryService.listAll());
        return "notes";
    }

    @GetMapping("/add")
    public String addNoteForm(Model model) {
        NoteForm noteForm = new NoteForm();
        model.addAttribute("noteForm", noteForm);
        model.addAttribute("categories", categoryService.listAll());
        return "add_note";
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute("noteForm") NoteForm noteForm) {
        noteService.add(noteMapper.toNoteEntity(noteForm));
        return "redirect:/api/V1/notes/list";
    }

    @GetMapping("/edit")
    public String editNoteForm(@RequestParam("id") UUID id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("noteForm", noteMapper.toNoteForm(note));
        model.addAttribute("categories", categoryService.listAll());
        return "edit_note";
    }

    @PostMapping("/edit")
    public String editNote(@ModelAttribute("noteForm") NoteForm noteForm) {
        noteService.update(noteForm.getId(), noteMapper.toNoteEntity(noteForm));
        return "redirect:/api/V1/notes/list";
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam("id") UUID id) {
        noteService.deleteById(id);
        return "redirect:/api/V1/notes/list";
    }
}
