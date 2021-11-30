package cn.edu.cqu;

import cn.edu.cqu.anotation.Bean;
import cn.edu.cqu.anotation.ComponentScan;
import cn.edu.cqu.anotation.Configuration;


@Configuration
@ComponentScan
public class Application {

//	@Bean
//	MessageService mockMessageService() {
//		return new MessageService() {
//			public String getMessage() {
//				return "mockMessageService:Hello World!";
//			}
//		};
//	}
//	@Bean
//	UserService userService(){
//		return new UserService(){
//			public String print(){
//				return "123";
//			}
//		};
//	}
	@Bean
	String buffer() {
		return "Appliccation:buffer";
	}

}