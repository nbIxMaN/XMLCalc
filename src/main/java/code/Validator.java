package code;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.List;

class Validator {
    private final static Logger logger = Logger.getLogger(Validator.class);

    private boolean argumentValidate(BigInteger argument) {
        if (argument == null)
            return false;
        else if (argument.signum() < 0) {
            logger.error(String.format("Argument %d", argument.longValue()));
            return false;
        } else
            return true;
    }

    private boolean argumentsValidate(List<BigInteger> arguments) {
        return arguments != null && arguments.size() == 2 && argumentValidate(arguments.get(0)) && argumentValidate(arguments.get(1));
    }

    private boolean operationValidate(List<Term> operations) {
        return operations != null && operations.size() == 2;
    }

    private boolean argumentOrOperation(BigInteger argument, Term operation) {
        return argumentValidate(argument) || operation != null;
    }

    boolean termValidate(Term term) {
        return argumentsValidate(term.arg) || operationValidate(term.operation) ||
                (argumentOrOperation(term.arg1, term.operation1) && argumentOrOperation(term.arg2, term.operation2));
    }
}
