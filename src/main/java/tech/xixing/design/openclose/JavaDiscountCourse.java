package tech.xixing.design.openclose;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/30 17:22
 */
public class JavaDiscountCourse extends JavaCourse {


    public JavaDiscountCourse(Integer id, String name, Double price) {
        super(id, name, price);
    }


    public Double getOriginPrice(){
        return super.getPrice();
    }

    @Override
    public Double getPrice(){

        return super.getPrice()*0.8;
    }

}
