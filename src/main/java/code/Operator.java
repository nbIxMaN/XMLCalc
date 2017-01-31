package code;

import java.math.BigDecimal;

public enum Operator {
    SUM("SUM") {
        @Override
        public BigDecimal apply(BigDecimal leftOperand, BigDecimal rightOperand) {
            return leftOperand.add(rightOperand);
        }
    },
    SUB("SUB") {
        @Override
        public BigDecimal apply(BigDecimal leftOperand, BigDecimal rightOperand) {
            return leftOperand.subtract(rightOperand);
        }
    },
    MUL("MUL") {
        @Override
        public BigDecimal apply(BigDecimal leftOperand, BigDecimal rightOperand) {
            return leftOperand.multiply(rightOperand);
        }
    },
    DIV("DIV") {
        @Override
        public BigDecimal apply(BigDecimal leftOperand, BigDecimal rightOperand) {
            return leftOperand.divide(rightOperand, 20, BigDecimal.ROUND_CEILING);
        }
    };
    private final String operator;

    private Operator(String operator) {
        this.operator = operator;
    }

    public static Operator buildOperator(String code) {
        for (Operator operator : Operator.values()) {
            if (code.equals(operator.operator)) {
                return operator;
            }
        }
        throw new IllegalArgumentException(String.format("Illegal operation %s", code));
    }

    public abstract BigDecimal apply(BigDecimal leftOperand, BigDecimal rightOperand);
}
