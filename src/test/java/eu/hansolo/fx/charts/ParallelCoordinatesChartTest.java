/*
 * Copyright (c) 2018 by Gerrit Grunwald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hansolo.fx.charts;

import eu.hansolo.fx.charts.data.ChartItem;
import eu.hansolo.fx.charts.data.DataObject;
import eu.hansolo.fx.charts.tools.Helper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * User: hansolo
 * Date: 29.01.18
 * Time: 09:11
 */
public class ParallelCoordinatesChartTest extends Application {
    private List<Car>                cars;
    private ParallelCoordinatesChart chart;

    @Override public void init() {
        Car smartRoadster = new Car("Smart Roadster Coupé","Smart", "Roadster");
        Car golf          = new Car("Golf GTI", "VW", "Golf");
        Car passat        = new Car("VW Passat", "VW", "Passat");
        Car mclass        = new Car("Mercedes M 320", "Mercedes", "M");
        Car sls           = new Car("Mercedes SLS", "Mercedes", "SLS");
        Car nine11        = new Car("Porsche 911 Carrera 4", "Porsche", "911");
        Car veyron        = new Car("Bugatti Veyron", "Bugatti", "Veyron");

        setParameters(smartRoadster, 3, 82, 11, 880, 4.0, Color.BLUE);
        setParameters(golf, 4, 120, 9, 1200, 6.0, Color.RED);
        setParameters(passat, 4, 140, 8, 1300, 6.5, Color.GREEN);
        setParameters(mclass, 6, 185, 6.5, 1350, 9.5, Color.CYAN);
        setParameters(sls, 8, 560, 2.9, 1150, 17, Color.LIME);
        setParameters(nine11, 6, 346, 3.8, 1100, 13.0, Color.MAGENTA);
        setParameters(veyron, 16, 1001, 2.5, 1500, 23.0, Color.PURPLE);

        chart = ParallelCoordinatesChartBuilder.create()
                                               .items(smartRoadster, golf, passat, mclass, sls, nine11, veyron)
                                               .tickMarksVisible(false)
                                               .build();
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane(chart);
        pane.setPadding(new Insets(10));

        Scene scene = new Scene(pane);

        stage.setTitle("Parallel Coordinates Chart");
        stage.setScene(scene);
        stage.show();
    }

    @Override public void stop() {
        System.exit(0);
    }

    private void setParameters(final Car CAR, final double CYLINDER, final double POWER, final double ACCELERATION, final double WEIGHT, final double CONSUMPTION, final Color STROKE) {
        CAR.setCylinder(CYLINDER);
        CAR.setPower(POWER);
        CAR.setAcceleration(ACCELERATION);
        CAR.setWeight(WEIGHT);
        CAR.setConsumption(CONSUMPTION);
        CAR.setStroke(STROKE);

        CAR.getProperties().get("Power").setUnit("hp");
        CAR.getProperties().get("Weight").setUnit("kg");
        CAR.getProperties().get("Acceleration").setUnit("sec");
        CAR.getProperties().get("Consumption").setUnit("l/100km");
    }


    // ******************** Inner Classes *************************************
    class Car implements DataObject {
        private final String                 NAME;
        private final String                 MODEL;
        private final String                 VENDOR;
        private       ChartItem              cylinder;
        private       ChartItem              power;
        private       ChartItem              acceleration;
        private       ChartItem              weight;
        private       ChartItem              consumption;
        private       Paint                  fill;
        private       Color                  stroke;
        private       Map<String, ChartItem> properties;


        public Car(final String NAME, final String VENDOR, final String MODEL) {
            this.NAME    = NAME;
            this.VENDOR  = VENDOR;
            this.MODEL   = MODEL;
            cylinder     = new ChartItem("Cylinder");
            power        = new ChartItem("Power");
            acceleration = new ChartItem("Acceleration");
            weight       = new ChartItem("Weight");
            consumption  = new ChartItem("Consumption");

            properties = new HashMap<>();
            properties.put(cylinder.getName(), cylinder);
            properties.put(power.getName(), power);
            properties.put(acceleration.getName(), acceleration);
            properties.put(weight.getName(), weight);
            properties.put(consumption.getName(), consumption);

            fill   = Color.TRANSPARENT;
            stroke = Color.BLUE;
        }

        public String getVendor() { return VENDOR; }

        public String getModel() { return MODEL; }

        public double getCylinder() { return cylinder.getValue(); }
        public void setCylinder(final double CYLINDER) { cylinder.setValue((int) Helper.clamp(0, Double.MAX_VALUE, CYLINDER));}

        public double getPower() { return power.getValue(); }
        public void setPower(final double POWER) { power.setValue(Helper.clamp(0, Double.MAX_VALUE, POWER)); }

        public double getAcceleration() { return acceleration.getValue(); }
        public void setAcceleration(final double ACCELERATION) { acceleration.setValue(Helper.clamp(0, Double.MAX_VALUE, ACCELERATION)); }

        public double getWeight() { return weight.getValue(); }
        public void setWeight(final double WEIGHT) { weight.setValue(Helper.clamp(0, Double.MAX_VALUE, WEIGHT)); }

        public double getConsumption() { return consumption.getValue(); }
        public void setConsumption(final double CONSUMPTION) { consumption.setValue(Helper.clamp(0, Double.MAX_VALUE, CONSUMPTION)); }

        @Override public String getName() { return NAME; }

        @Override public Paint getFill() { return fill; }
        @Override public void setFill(final Paint FILL) { fill = FILL; }

        @Override public Color getStroke() { return stroke; }
        @Override public void setStroke(final Color STROKE) { stroke = STROKE; }

        @Override public Map<String, ChartItem> getProperties() { return properties; }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
