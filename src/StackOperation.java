import java.util.LinkedList;

public class StackOperation extends Word{
    public StackOperation(String string) {
        super(string);
    }

    public LinkedList<WordBox<Word>> runOperation(LinkedList<WordBox<Word>> stack){
        switch (super.getString()) {
            case "+":
                WordBox<Word> addNum1 = stack.removeLast();
                WordBox<Word> addNum2 = stack.removeLast();
                if (addNum1.getWord() instanceof Number && addNum2.getWord() instanceof Number) {
                    String n = addNum1.getWord().putEmTogether(addNum2.getWord().getString());
                    stack.addLast(new WordBox<>(new Number(n)));
                } else {
                    if (addNum1.getWord() instanceof Number){
                        addNum1 = new WordBox<Word>(new Quote(addNum1.getWord().getString()));
                    }
                    else if (addNum2.getWord() instanceof Number){
                        addNum2 = new WordBox<Word>(new Quote(addNum2.getWord().getString()));
                    }
                    String newWord = (addNum1.getWord()).putEmTogether((addNum2.getWord()).getString());
                    stack.addLast(new WordBox<Word>(new Quote(newWord)));
                }
                return stack;
            case "-":
                WordBox<Word> negatedNumber = stack.removeLast();
                if (negatedNumber.getWord() instanceof Number) {
                    Number n = new Number((negatedNumber.getWord()).negate());
                    stack.addLast(new WordBox<Word>(n));
                    return stack;
                }
                else if (negatedNumber.getWord() instanceof Quote) {
                    String n = ( negatedNumber.getWord()).negate();
                    stack.addLast(new WordBox<Word>(new Quote(n)));
                    return stack;
                }
            case "*":
                WordBox<Word> firstFactor = stack.removeLast();
                WordBox<Word> secondFactor = stack.removeLast();
                if (firstFactor.getWord() instanceof Number && secondFactor.getWord() instanceof Number) {
                    String n = ((Number) firstFactor.getWord()).multiply(secondFactor.getWord().getString());
                    stack.addLast(new WordBox<Word>(new Number(n)));
                } else {
                    if (firstFactor.getWord() instanceof Number){
                        firstFactor = new WordBox<Word>(new Quote(firstFactor.getWord().getString()));
                    }
                    else if (secondFactor.getWord() instanceof Number){
                        secondFactor = new WordBox<Word>(new Quote(secondFactor.getWord().getString()));
                    }
                    String newWord = (firstFactor.getWord()).multiply((secondFactor.getWord()).getString());
                    stack.addLast(new WordBox<Word>(new Quote(newWord)));
                }
                return stack;
            case "dup":
                WordBox<Word> dupNumber = stack.get(stack.size()-1);
                stack.addLast(dupNumber);
                return stack;
            case "swap":
                WordBox<Word> firstNum = stack.removeLast();
                WordBox<Word> secondNum = stack.removeLast();
                stack.addLast(firstNum);
                stack.addLast(secondNum);
                return stack;
            case "pop":
                stack.removeLast();
                return stack;
            default:
                return stack;
        }
    }
}