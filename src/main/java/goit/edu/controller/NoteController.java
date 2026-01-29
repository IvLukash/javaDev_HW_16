package goit.edu.controller;

import goit.edu.model.NoteDto;
import goit.edu.service.NoteService;
import goit.edu.webheloer.UserCookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {
    private final NoteService service;
    private final UserCookie userCookie;

    @GetMapping("/list")
    public String getAllNotes(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UUID userId = userCookie.getOrCreateUserId(request, response);
        model.addAttribute("notes", service.listAllByUserId(userId));
        return "all_notes";
    }

    @GetMapping("/get")
    public String getNoteById(
            Model model,
            @RequestParam("id") String idParam,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UUID userId = userCookie.getOrCreateUserId(request, response);
        Long id = Long.parseLong(idParam);
        model.addAttribute("note", service.getById(id, userId));
        return "get_note";
    }

    @GetMapping("/new")
    public String getCreateForm(Model model) {
        model.addAttribute("note", new NoteDto());
        return "create_form";
    }

    @PostMapping("/new")
    public String createNote(
            @ModelAttribute("note") NoteDto noteDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UUID userId = userCookie.getOrCreateUserId(request, response);
        service.add(noteDto, userId);
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public String getEditForm(
            Model model,
            @RequestParam("id") String idParam,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UUID userId = userCookie.getOrCreateUserId(request, response);
        Long id = Long.parseLong(idParam);
        model.addAttribute("note", service.getById(id, userId));
        return "edit_form";
    }

    @PostMapping("/edit")
    public String updateNote(
            @RequestParam("id") Long id,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("note") NoteDto noteDto
    ) {
        UUID userId = userCookie.getOrCreateUserId(request, response);
        service.update(id, userId, noteDto);
        return "redirect:/note/list";
    }

    @PostMapping("/delete")
    public String deleteNote(
            @RequestParam("id") Long id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UUID userId = userCookie.getOrCreateUserId(request, response);
        service.deleteById(id, userId);
        return "redirect:/note/list";
    }
}
