package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    /*
    JPQL의 : bno에는 아래 listOfBoard의 매개변수값이 Long bno값이 전달된다.
     */
    @Query("select r from Reply r where r.board.bno = :bno")
    Page<Reply> listOfBoard(Long bno, Pageable pageable);

    /*
    JPA의 DML을 사용하기 위한 3가지 방법(다른 방법도 더 있다)

    모두 JPQL -> Native Query -> DBMS 서버에 전달

    단순 ------------------ 복잡
    쿼리 메서드    JPQL    Querydsl

    1) 쿼리 메서드
    기본 CRUD는 자동으로 이미 만들어져있다.
    Repository 인터페이스에 함수명명규칙에 따라 이름을 정하면, 자동으로 JPA가 JPQL로 만들어준다.
    2) JPQL
    SQL문과 거의 유사하다.
    이것이 나온 이유는 각 DBMS마다 약간씩 SQL이 다르므로 JPA에서 사용하는 표준을 정해놓은 것.
    3) Querydsl
    조건이 복잡할 때, 메서드 호출방식으로 개발자가 조합하기 익숙하도록 제공하는 방식
    제어문이 필요해서
    (ex. Oracle의 PL-SQL의 역할)
     */
    /*
    함수명명규칙을 따라 메서드를 선언하면 JPA에 의해 자동으로 구현된다.
    https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
     */

    /*
    아래 함수는 자동으로 JPQL로 변환된다.
    DELETE FROM Reply r WHERE r.board_bno=:bno
     */
    void deleteByBoard_Bno(Long bno);

    /*
    아래처럼 함수명명규칙을 잘 지켜서 선언하면
    JPA에서 자동으로 JPQL로 변환하는 메서드를 구현해준다.
    SELECT * FROM Reply r where r.replyText = :replyText and r.replyer = :replyer
     */

    Page<Reply> findByReplyTextAndReplyer(String replyText, String replyer, Pageable pageable);


}
