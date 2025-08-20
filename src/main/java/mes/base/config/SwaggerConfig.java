package mes.base.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Swagger 문서 화면을 커스터마이징하기 위한 설정
// Swagger UI 페이지에 나오는 제목, 설명, 버전 등을 설정하는 역할
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("MES_base_spring API") // 문서 제목
                                .version("1.0.0") // API 버전
                                .description("MES_base_spring 서비스에 대한 Swagger 문서입니다.")); //설명
    }
}

