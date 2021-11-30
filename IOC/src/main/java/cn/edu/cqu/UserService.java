package cn.edu.cqu;

import cn.edu.cqu.anotation.Component;

import java.util.ArrayList;
import java.util.List;
public @interface UserService{
     String print();
}
//public class UserService {
//    public  String print(){
////        System.out.println("’‚ «UserService£°");
//        return "uservice";
//    }
//    public User getUser( ) {
//        return new User("U_TEST","123");
//    }
//    public List<User> getList()
//    {
//        User user1 = new User("u1","123");
//        User user2 = new User("u2","123");
//        User user3 = new User("u3","123");
//        List<User> userList = new ArrayList<>();
//        userList.add(user1);
//        userList.add(user2);
//        userList.add(user3);
//        return  userList;
//    }
//}
