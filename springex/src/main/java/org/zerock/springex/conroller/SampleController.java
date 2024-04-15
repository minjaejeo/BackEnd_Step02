package org.zerock.springex.conroller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// (Page)Controller 역할을 하는 bean으로 생성부탁해
@Controller
@Log4j2
public class SampleController {

    // /hello에 get요청이 들어오면 이 메서드가 처리한다.
    @GetMapping("/hello")
    public void hello(){
        log.info("hello");

        // 리턴이 없으면 자동으로 /WEB-INF/views/hello.jsp로 이동하게 된다.
    }


}
