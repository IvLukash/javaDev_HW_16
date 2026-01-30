package goit.edu.controller;

import goit.edu.model.dto.NoteDto;
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
    private static final String USER_ID = "user_id";
    private final NoteService service;
    private final UserCookie userCookie;

    @GetMapping
    public String getStartPage(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        userCookie.createUserId(request, response);
        return "index";
    }

    @GetMapping("/list")
    public String getAllNotes(
            Model model,
            @CookieValue(value = USER_ID, required = false) String userIdParam
    ) {
        if (userIdParam == null) {
            return "redirect:/note";
        }
        UUID userId = UUID.fromString(userIdParam);
        model.addAttribute("notes", service.listAllByUserId(userId));
        return "all_notes";
    }

    @GetMapping("/get/{id}")
    public String getNoteById(
            Model model,
            @PathVariable("id") String idParam,
            @CookieValue(value = USER_ID, required = false) String userIdParam
    ) {
        UUID userId = UUID.fromString(userIdParam);
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
            @CookieValue(value = USER_ID, required = false) String userIdParam
    ) {
        UUID userId = UUID.fromString(userIdParam);
        service.add(noteDto, userId);
        return "redirect:/note/list";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(
            Model model,
            @PathVariable("id") String idParam,
            @CookieValue(value = USER_ID, required = false) String userIdParam
    ) {
        UUID userId = UUID.fromString(userIdParam);
        Long id = Long.parseLong(idParam);
        model.addAttribute("note", service.getById(id, userId));
        return "edit_form";
    }

    @PostMapping("/edit/{id}")
    public String updateNote(
            @PathVariable("id") Long id,
            @CookieValue(value = USER_ID, required = false) String userIdParam,
            @ModelAttribute("note") NoteDto noteDto
    ) {
        UUID userId = UUID.fromString(userIdParam);
        service.update(id, userId, noteDto);
        return "redirect:/note/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(
            @PathVariable("id") Long id,
            @CookieValue(value = USER_ID, required = false) String userIdParam
    ) {
        UUID userId = UUID.fromString(userIdParam);
        service.deleteById(id, userId);
        return "redirect:/note/list";
    }
}
