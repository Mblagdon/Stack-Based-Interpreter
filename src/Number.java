public class Number extends Word {
    private final long value;

    public Number(String string) {
        super(string);
        this.value = Long.parseLong(string);
    }

    public Number(long value) {
        super(String.format("%d", value));
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String putEmTogether(String x) {
        Number firstNumber  = new Number(x);
        Number secondNumber = new Number(this.value + firstNumber.getValue());
        return secondNumber.getString();
    }

    @Override
    public String negate() {
        Number negatedNumber = new Number(this.value * -1);
        return negatedNumber.getString();
    }

    @Override
    public String multiply(String y) {
        Number firstNumber = new Number(y);
        Number product = new Number(this.value * firstNumber.getValue());
        return product.getString();
    }
}