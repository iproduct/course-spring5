package org.iproduct.ml.h2o.services;

import hex.genmodel.GenModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;
import lombok.extern.slf4j.Slf4j;
import org.iproduct.ml.h2o.domain.RecognitionResult;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class DnnRecognizerService {
    private static final String COMMA_DELIMITER = ",";
    private static String modelClassName = "org.iproduct.ml.h2o.mlmodels.deeplearning.deeplearning_012ff792_aaa7_460f_ba40_c66e47e420ac";

    private EasyPredictModelWrapper model;
    List<String> names = new ArrayList<>();;

    @PostConstruct
    public void init() {
        try {
            GenModel rawModel = (hex.genmodel.GenModel) Class.forName(modelClassName).newInstance();
            model = new EasyPredictModelWrapper(rawModel);

            List<List<String>> records = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/deeplearning/embeddings.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(COMMA_DELIMITER);
                    records.add(Arrays.asList(values));
                }
            }

            for(int i = 1; i < records.size(); i++){
                if(names.size() == 0 || !names.get(names.size()-1).equals(records.get(i).get(0))){
                    names.add(records.get(i).get(0));
                }
            }
            log.info("Names to recognize: " + names);

        } catch(Exception e){
            log.error("Error mapping value to JSON:", e);
            throw new RuntimeException(e);
        }

    }

    public RecognitionResult recognize(float[] embeddings) throws PredictException {
//        List<String> myPictureEmbedings = records.get(2000);

        RowData row = new RowData();
        Locale.setDefault(Locale.US);
        for(int i = 0; i < embeddings.length; i++) {
            row.put(i + "", String.format("%12.10e", embeddings[i]));
        }

        MultinomialModelPrediction p = model.predictMultinomial(row);
        System.out.println("\nPredicted class label: " + p.label);
        System.out.printf("Predicted class: %d -> %s (%f)%n", p.labelIndex, p.label, p.classProbabilities[p.labelIndex]);
        System.out.print("Class probabilities: ");
        for (int i = 0; i < p.classProbabilities.length; i++) {
            if (i > 0) {
                System.out.print(",");
            }
            System.out.print(p.classProbabilities[i]);
        }
        System.out.println("");
        return new RecognitionResult(p.label, p.labelIndex,
                p.classProbabilities[p.labelIndex], p.classProbabilities);
    }
}
