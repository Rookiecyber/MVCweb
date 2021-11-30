package cn.edu.cqu;

import cn.edu.cqu.anotation.Autowired;
import cn.edu.cqu.anotation.Component;

@Component
public class Boss {

    @Autowired
    private Car car;

    @Autowired
    private Office office;

}
