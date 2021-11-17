//package cn.edu.cqu;
//
//import java.util.Map;
//
//public class IOCcontainer{
//    private Map<String, Object> map = new HashMap<>();
//
//    public ApplicationContex() {
//    }
//    public void addAnnotatedClass(Class<?> clz)
//    {
//        Component component=clz.getAnnotation(Component.class);
//        try {
//            Object obj=clz.newInstance();
//            map.put(component.name(), obj);
//            Field[] fields=clz.getDeclaredFields();
//            for(Field f:fields)
//            {
//                Autowired autowired=f.getAnnotation(Autowired.class);
//                if(autowired!=null)
//                {
//                    f.setAccessible(true);
//                    f.set(obj, map.get(autowired.name()));
//                }
//            }
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//    public Object getBean(String beanName) {
//        return map.get(beanName);
//    }
//
//
//}
