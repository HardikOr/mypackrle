import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class MyPackRle {
    @Option(name="-z",usage="zip file")
    private boolean zip;

    @Option(name="-u",usage="zip file")
    private boolean unzip;

    @Option(name="-out",usage="Output name file")
    private String outputFileName = "";

    @Argument(required = true, metaVar = "InputName", usage = "Input file name")
    private String inputFileName;

    public static void main(String[] args) {
        new MyPackRle().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("Wrong");
            parser.printUsage(System.err);
            return;
        }

        if (zip) {
            if (outputFileName == "")
                Pack.zip(inputFileName, inputFileName.substring(0, inputFileName.lastIndexOf(".")) + ".out");
            else
                Pack.zip(inputFileName, outputFileName);
        }

        if (unzip) {
            if (outputFileName == "")
                Pack.unzip(inputFileName, inputFileName.substring(0, inputFileName.lastIndexOf(".")) + ".out");
            else
                Pack.unzip(inputFileName, outputFileName);
        }
    }
}
