package org.zerock.springex.config;


import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;

public class ModelMapperConfig {

    // ModelMapper클래스를 Bean으로 생성해서 Spring Container에 포함시킨다.
    @Bean
    public ModelMapper getMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;

    }
}
