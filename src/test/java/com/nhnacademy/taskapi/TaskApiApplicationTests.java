package com.nhnacademy.taskapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
class TaskApiApplicationTests {

    @Test
    void contextLoads() {
        assertThatCode(() -> {
            // 이 테스트는 애플리케이션 컨텍스트가 성공적으로 로드되는지만 확인합니다.
        }).doesNotThrowAnyException();
    }

}
