/**
 * Number class extends Word
 * This class is used to represent numbers
 *
 * @author  MBlagdon
 */
public class Number extends Word {
    private final long value;
    /**
     * Constructor for Number class
     *
     * @param string representation of number
     */
    public Number(String string) {
        super(string);
        this.value = Long.parseLong(string);
    }
    /**
     * Constructor for Number class
     *
     * @param value representation of the number
     */
    public Number(long value) {
        super(String.format("%d", value));
        this.value = value;
    }
    /**
     * Getter for number's value
     *
     * @return value of the number
     */
    public long getValue() {
        return value;
    }
    /**
     * Method to add value of a number and another
     *
     * @param additionalString representation of number to add
     * @return representation of the sum
     */
    @Override
    public String putEmTogether(String additionalString) {
        Number firstNumber  = new Number(additionalString);
        Number secondNumber = new Number(this.value + firstNumber.getValue());
        return secondNumber.getString();
    }
    /**
     * Method to negate value of a number
     *
     * @return representation of negated number
     */
    @Override
    public String negate() {
        Number negatedNumber = new Number(this.value * -1);
        return negatedNumber.getString();
    }
    /**
     * Method to multiply value of a number with another
     *
     * @param targetString representation of number to multiply with
     * @return representation of the product
     */
    @Override
    public String multiply(String targetString) {
        Number firstNumber = new Number(targetString);
        Number product = new Number(this.value * firstNumber.getValue());
        return product.getString();
    }
}