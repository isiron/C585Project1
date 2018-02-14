/*
 * Name: Ivan Suarez
 * Date: 2/11/2018
 * Class name: Calculator.java
 * Purpose: Taking in values and calculating results based on method, storing the value
 */
public class Calculator {
    private int value;

    public Calculator(){
        value = 0;
    }

    public void plus(int input){
        value += input;
    }
    public void minus(int input){
        value -= input;
    }
    public void multiply(int input){
        value *= input;
    }
    public void divide(int input){
        value /= input;
    }
    public void mod(int input){
        value %= input;
    }
    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value = value;
    }
    public void resetValue(){
        value = 0;
    }
}
