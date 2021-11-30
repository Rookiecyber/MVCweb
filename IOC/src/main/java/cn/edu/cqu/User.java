package cn.edu.cqu;

import cn.edu.cqu.anotation.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private  String username;
    private  String password;
}
