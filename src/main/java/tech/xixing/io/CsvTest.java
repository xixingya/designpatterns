package tech.xixing.io;

import java.io.*;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class CsvTest {

    public static void main(String[] args) {

        String csvFile = "/data/app.csv";
        String line = "";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
             FileWriter fw = new FileWriter("/data/output_app.csv")) {

            while ((line = br.readLine()) != null) {
                String[] values = line.split(csvSplitBy);

                // 处理每一行数据
                for (String value : values) {
                    fw.append(value + ",");
                }
                fw.append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
