/*
 * Copyright 2023 Esri.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.esri.samples.add_custom_dynamic_entity_data_source;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.arcgisservices.LabelDefinition;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.DynamicEntityLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.labeling.SimpleLabelExpression;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.TextSymbol;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddCustomDynamicEntityDataSourceSample extends Application {

  private MapView mapView = new MapView();

  private SimulatedDataSource dynamicEntityDataSource;

  @Override
  public void start(Stage stage) {
    StackPane stackPane = new StackPane();
    Scene scene = new Scene(stackPane);

    // Set title, size, and add scene to stage
    stage.setTitle("Add custom dynamic entity data source");
    stage.setWidth(800);
    stage.setHeight(700);
    stage.setScene(scene);
    stage.show();

    // Authentication with an API key or named user is required to access basemaps and other location services
    String yourAPIKey = System.getProperty("apiKey");
    ArcGISRuntimeEnvironment.setApiKey(yourAPIKey);

    // Create a new map with the navigation basemap style.
    ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_OCEANS);

    // Create a map view and set the map to it
    mapView = new MapView();
    mapView.setMap(map);

    // Set the initial viewpoint.
    mapView.setViewpoint(new Viewpoint(new Point(-123.657, 47.984, SpatialReferences.getWgs84()), 3e6));

    // Create a custom data source implementation of a DynamicEntityDataSource.
    // This takes the path to the simulation file, field name that will be used as the entity id, and the delay between
    // each observation that is processed.
    // In this example we are using a json file (stored as a resource) as our custom data source.
    // This field value should be a unique identifier for each entity.
    // Adjusting the value for the delay will change the speed at which the entities and their observations are displayed.
    var resource = getClass().getResource("/add_custom_dynamic_entity_data_source/AIS_MarineCadastre_SelectedVessels_CustomDataSource.json");
    dynamicEntityDataSource = new SimulatedDataSource(resource.getPath(), "MMSI", 10);

    // Create the dynamic entity layer using the custom data source.
    var dynamicEntityLayer = new DynamicEntityLayer(dynamicEntityDataSource);

    // Set up the track display properties.
    setupTrackDisplayProperties(dynamicEntityLayer);

    // Set up the dynamic entity labeling.
    setupLabeling(dynamicEntityLayer);

    // Add the dynamic entity layer to the map.
    map.getOperationalLayers().add(dynamicEntityLayer);

    // Add the map view to the stack pane
    stackPane.getChildren().addAll(mapView);
  }

  /**
   *  Set up the track display properties, these properties will be used to configure the appearance of the track line
   *  and previous observations.
   *
   * @param layer the DynamicEntityLayer to be configured
   */
  private void setupTrackDisplayProperties(DynamicEntityLayer layer) {
    var trackDisplayProperties = layer.getTrackDisplayProperties();
    trackDisplayProperties.setShowPreviousObservations(true);
    trackDisplayProperties.setShowTrackLine(true);
    trackDisplayProperties.setMaximumDuration(200);
  }

  /**
   * Configure labeling on the layer to use a red label using the VesselName attribute.
   *
   * @param layer the DynamicEntityLayer for the labels
   */
  private void setupLabeling(DynamicEntityLayer layer) {
    // Define the label expression to be used, in this case we will use the "VesselName" for each of the dynamic entities.
    var simpleLabelExpression = new SimpleLabelExpression("[VesselName]");

    // Set the text symbol color and size for the labels.
    var labelSymbol = new TextSymbol(12, "", Color.RED, TextSymbol.HorizontalAlignment.CENTER, TextSymbol.VerticalAlignment.BOTTOM);

    // Set the label position.
    var labelDef = new LabelDefinition(simpleLabelExpression, labelSymbol);

    // Add the label definition to the dynamic entity layer and enable labels.
    layer.getLabelDefinitions().add(labelDef);
    layer.labelsEnabledProperty().set(true);
  }

  /**
   * Stops and releases all resources used in application.
   */
  @Override
  public void stop() {
    // Notify the observations timer thread to stop.
    if (dynamicEntityDataSource != null) {
      dynamicEntityDataSource.stopObservations();
    }

    // Release resources.
    if (mapView != null) {
      mapView.dispose();
    }
  }

  /**
   * Opens and runs application.
   *
   * @param args arguments passed to this application
   */
  public static void main(String[] args) {
    launch(args);
  }
}
