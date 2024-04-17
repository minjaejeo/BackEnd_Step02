package org.zerock.springex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springex.dto.PageRequestDTO;
import org.zerock.springex.dto.TodoDTO;
import org.zerock.springex.service.TodoService;

import javax.validation.Valid;

@Controller
@RequestMapping("/todo")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // /todo/list
    @RequestMapping("/list")
    public void list(@Valid PageRequestDTO pageRequestDTO,
                     BindingResult bindingResult,
                     Model model){
        log.info("todo list...........");

        if(bindingResult.hasErrors()){
            // 디폴트 값을 가지게 된다.(page=1, size=10)
            // 첫번째 페이지가 나오도록
            pageRequestDTO = PageRequestDTO.builder().build();
        }
        // PageRequestDTO를 todoService.getList에 넘겨주면, PageResponseDTO를 리턴한다.
        // 이 리턴된 값을 model -> request -> jsp에 전달
        // 이 전달된 responseDTO를 jsp가 꺼내서 boostrap의 pagination 컴포턴트를 구성
        model.addAttribute("responseDTO", todoService.getList(pageRequestDTO));
        
//        model.addAttribute("dtoList", todoService.getAll());

        // /WEB-INF/views/todo/list.jsp
    }

    // /todo/register
    //@RequestMapping(value = "/register", method = RequestMethod.GET)
    @GetMapping("/register")
    public void register(){
        log.info("GET todo register............");

        // /WEB-INF/views/todo/register.jsp
    }

//    @PostMapping("/register")
//    public void registerPost(){
//        log.info("POST todo register............");
//    }

    /* 웹에서 보내오는 parameter들이
    TodoDTO내부의 필드들의 이름과 매칭되면 todoDTO객체 내부에 저장된다.
    * */
    @PostMapping("/register")
    public String registerPost(@Valid TodoDTO todoDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        log.info("POST todo register............");

        /* todoDTO의 제약조건이 오류가 발생했을 때
         * */
        if(bindingResult.hasErrors()){
            log.info("has errors......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/todo/register";
        }

        log.info(todoDTO);

        todoService.register(todoDTO);

        return "redirect:/todo/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long tno, Model model){
        TodoDTO todoDTO = todoService.getOne(tno);
        log.info(todoDTO);

        model.addAttribute("dto", todoDTO);
    }

    @PostMapping("/remove")
    public String remove(Long tno, RedirectAttributes redirectAttributes){

        log.info("------------remove--------------------");
        log.info("tno: " + tno);

        todoService.remove(tno);

        return "redirect:/todo/list";
    }

    @PostMapping("/modify")
    public String modify(@Valid TodoDTO todoDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            log.info("has errors......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("tno", todoDTO.getTno());
            return "redirect:/todo/modify";
        }

        log.info(todoDTO);
        todoService.modify(todoDTO);

        return "redirect:/todo/list";
    }
}














