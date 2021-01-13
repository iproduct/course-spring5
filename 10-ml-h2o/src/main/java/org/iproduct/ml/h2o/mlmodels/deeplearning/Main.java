package org.iproduct.ml.h2o.mlmodels.deeplearning;

import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.prediction.BinomialModelPrediction;
import hex.genmodel.easy.prediction.ClusteringModelPrediction;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;
import hex.genmodel.easy.prediction.OrdinalModelPrediction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String COMMA_DELIMITER = ",";
    private static String modelClassName = "org.iproduct.ml.h2o.mlmodels.deeplearning.deeplearning_012ff792_aaa7_460f_ba40_c66e47e420ac";

    public static void main(String[] args) throws Exception {
        hex.genmodel.GenModel rawModel;
        rawModel = (hex.genmodel.GenModel) Class.forName(modelClassName).newInstance();
        EasyPredictModelWrapper model = new EasyPredictModelWrapper(rawModel);

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/deeplearning/embeddings.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        }

        List<String> names = new ArrayList<>();
        for(int i = 1; i < records.size(); i++){
            if(names.size() == 0 || !names.get(names.size()-1).equals(records.get(i).get(0))){
                names.add(records.get(i).get(0));
            }
        }
        System.out.printf("Names: %s%n", names.toString());

        List<String> myPictureEmbedings = records.get(11);
        System.out.println(myPictureEmbedings);

        RowData row = new RowData();
        for(int i = 1; i < myPictureEmbedings.size(); i++) {
            row.put(i-1 + "", myPictureEmbedings.get(i));
        }

        MultinomialModelPrediction p = model.predictMultinomial(row);
        System.out.println("\nPredicted class index: " + p.labelIndex);
        System.out.println("Predicted class: " + p.label);
        System.out.print("Class probabilities: ");
        for (int i = 0; i < p.classProbabilities.length; i++) {
            if (i > 0) {
                System.out.print(",");
            }
            System.out.print(p.classProbabilities[i]);
        }
        System.out.println("");
    }
}

