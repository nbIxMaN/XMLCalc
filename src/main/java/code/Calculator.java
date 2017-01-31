package code;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;

class Calculator {

    private final static Logger logger = Logger.getLogger(Calculator.class);
    private final Validator validator = new Validator();

    private BigDecimal getArgument(BigInteger argument, Term operation) {
        if (argument != null)
            return new BigDecimal(argument);
        else
            return calculate(operation);
    }

    BigDecimal calculate(Term term) {
        BigDecimal result1;
        BigDecimal result2;

        if (term == null) {
            logger.error("Some arguments were not initialized");
            throw new IllegalArgumentException();
        } else if (!validator.termValidate(term)) {
            logger.error("Argument validation error");
            throw new IllegalArgumentException();
        } else if (term.arg != null) {
            result1 = new BigDecimal(term.arg.get(0));
            result2 = new BigDecimal(term.arg.get(1));
        } else if (term.operation != null) {
            result1 = calculate(term.operation.get(0));
            result2 = calculate(term.operation.get(1));
        } else {
            result1 = getArgument(term.arg1, term.operation1);
            result2 = getArgument(term.arg2, term.operation2);
        }
        return Operator.buildOperator(term.operationType).apply(result1, result2);
    }
}
