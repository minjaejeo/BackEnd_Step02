package org.zerock.jdbcex.dao;

import lombok.Cleanup;
import org.zerock.jdbcex.domain.TodoVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class TodoDAO {
    public String getTime(){
        String now = null;

        // try~with~Resources
        // try()안에서 생성한 객체를 try이 종료될 때, 자동으로 close가 호출되므로
        // finally구문을 사용자리 않아도 된다. 코드를 간결하게 하려는 목적
        try(Connection connection = ConnectionUtil.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select now(");
        ResultSet resultSet = preparedStatement.executeQuery();
        ){
            resultSet.next();
            now = resultSet.getString(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return now;
    }
    public String getTime2() throws Exception{
        String now = null;

        /*
        lombok에서 제공해주는 @Cleanup을 사용하면
        메서드가 종료될 때 자동으로 Close()를 호출해준다.
         */

        @Cleanup Connection connnection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connnection.prepareStatement("select now(");
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        now = resultSet.getString(1);

        return now;
    }
}























