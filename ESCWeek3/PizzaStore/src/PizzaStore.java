public class PizzaStore {

    public Pizza orderPizza (String type) {
        Pizza pizza;
        PizzaFactory newFac = new PizzaFactory();
        pizza = newFac.makePizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    public static void main(String[] args) {
        PizzaStore pizza1 = new PizzaStore();
        System.out.println(pizza1.orderPizza("C").toString());

        PizzaStore pizza2 = new PizzaStore();
        System.out.println(pizza2.orderPizza("P").toString());

    }
}

class Pizza {

    public void prepare() {
    }

    public void box() {
    }

    public void cut() {
    }

    public void bake() {
    }
}

class PizzaFactory {
    public Pizza makePizza(String pizzaType){
        if (pizzaType.equals("C")){
            return new CheesePizza();
        }
        if (pizzaType.equals("G")){
            return new GreekPizza();
        }
        if (pizzaType.equals("P")){
            return new PepperoniPizza();
        }
        else {
            return null;
        }
    }
}

class CheesePizza extends Pizza {}
class GreekPizza extends Pizza {}
class PepperoniPizza extends Pizza {}

