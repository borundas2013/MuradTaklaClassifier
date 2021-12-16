package com.example.muradtaklaclassifier;

public class ClassifierResult {
    private int prediction;
    private double[] prediction_probability;

    public ClassifierResult(int prediction, double[] prediction_probability) {
        this.prediction = prediction;
        this.prediction_probability = prediction_probability;
    }


}

