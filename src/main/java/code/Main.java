package code;

import javax.xml.bind.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Path data = Paths.get("").toAbsolutePath().resolve("data");

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Specify data file names");
        }
        for (String arg : args) {
            try {
                File inputFile = data.resolve(arg).toFile();
                if (!inputFile.exists()) {
                    throw new IllegalArgumentException(inputFile.getName() + " - Incorrect file name");
                }
                File outputFile = data.resolve("Result" + inputFile.getName()).toFile();
                JAXBContext jaxbContext = JAXBContext.newInstance(SimpleCalculator.class);

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                SimpleCalculator simpleCalculator = (SimpleCalculator) jaxbUnmarshaller.unmarshal(inputFile);
                Calculator calculator = new Calculator();
                List<SimpleCalculator.ExpressionResults.ExpressionResult> results = new ArrayList<>();
                ObjectFactory factory = new ObjectFactory();
                for (SimpleCalculator.Expressions.Expression ex : simpleCalculator.expressions.expression) {
                    results.add(factory.createSimpleCalculatorExpressionResultsExpressionResult(calculator.calculate(ex.operation).doubleValue()));
                    simpleCalculator.setExpressionResults(factory.createSimpleCalculatorExpressionResults(results));
                }

                simpleCalculator.expressions = null;

                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                jaxbMarshaller.marshal(simpleCalculator, outputFile);
            } catch (IllegalArgumentException | JAXBException e) {
                e.printStackTrace();
            }
        }
    }
}
