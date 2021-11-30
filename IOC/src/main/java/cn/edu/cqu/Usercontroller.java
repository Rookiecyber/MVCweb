package cn.edu.cqu;

import cn.edu.cqu.anotation.Autowired;
import cn.edu.cqu.anotation.Bean;
import cn.edu.cqu.anotation.Component;
import cn.edu.cqu.User;
import cn.edu.cqu.anotation.Configuration;


@Component
public class Usercontroller {
    @Autowired
    UserService userService;
    public void say()
    {
//        System.out.println("another message:");
        System.out.println(userService.print());
    }

}
