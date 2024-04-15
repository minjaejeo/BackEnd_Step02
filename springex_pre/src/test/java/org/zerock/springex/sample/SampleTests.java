package org.zerock.springex.sample;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/*
ServletContext : tomcat의 전역영역, tomcat의 Container가 관리하는 영역
ApplicationContext : Spring Container가 관리하는 영역, 이곳에 bean(관리되어지는 객체)를 저장한다.
 */

@Log4j2
@ExtendWith(SpringExtension.class)  // junit4 @RunWith
// root-context.xml을 읽어들여서 ApplicationContext 영역에 bean들을 생성한다.
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class SampleTests {

}
