import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductWriter
{

    public static void main(String[] args)
    {
        ArrayList<String> products = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        boolean done = false;

        do
        {
            String id = SafeInput.getNonZeroLenString(in, "Enter the Product ID");
            String name = SafeInput.getNonZeroLenString(in, "Enter the Product Name");
            String description = SafeInput.getNonZeroLenString(in, "Enter the Product Description");
            double cost = SafeInput.getDouble(in, "Enter the Product Cost");

            String rec = id + ", " + name + ", " + description + ", " + cost;
            products.add(rec);

            done = !SafeInput.getYNConfirm(in, "Do you want to add another product?");
        } while (!done);

        try
        {
            // uses a fixed known path:
            //  Path file = Paths.get("c:\\My Documents\\data.txt");

            // use the toolkit to get the current working directory of the IDE
            // Not sure if the toolkit is thread safe...
            File workingDirectory = new File(System.getProperty("user.dir"));
            Path file = Paths.get(workingDirectory.getPath() + File.separator + "ProductTestData.txt");

            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    Files.newOutputStream(file, CREATE);
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            for(String p : products)
            {
                writer.write(p, 0, p.length());
                writer.newLine();
            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("\n\nData file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}