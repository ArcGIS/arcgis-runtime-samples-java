/*
 * Copyright 2016 Esri.
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
package com.esri.samples.localserver;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.localserver.LocalFeatureService;
import com.esri.arcgisruntime.localserver.LocalGeoprocessingService;
import com.esri.arcgisruntime.localserver.LocalMapService;
import com.esri.arcgisruntime.localserver.LocalServer;
import com.esri.arcgisruntime.localserver.LocalServerStatus;
import com.esri.arcgisruntime.localserver.LocalService;
import com.esri.arcgisruntime.util.ListenableList;

public class LocalServerServicesController {

  @FXML
  private Button btnStartServer;
  @FXML
  private Button btnStopServer;
  @FXML
  private Button btnStartService;
  @FXML
  private Button btnStopService;
  @FXML
  private Button btnURL;
  @FXML
  private ComboBox<String> serviceOptions;
  @FXML
  private TextArea centerText;
  @FXML
  private ListView<String> runningServices;

  private boolean isServerStopping;

  private LocalServer server;
  private ListenableList<LocalService> services;
  private HostServices hostServices;

  /**
   * Handles starting and stopping a local server.
   * 
   * @param event the event that fired this action
   */
  @FXML
  protected void handleStartStopLocalServer(ActionEvent event) {

    // find out what fired this event
    EventTarget eventTarget = event.getTarget();

    // starting server
    if (eventTarget.equals(btnStartServer)) {
      // start local server
      server = LocalServer.INSTANCE;
      isServerStopping = false;
      server.addStatusChangedListener(status -> {
        centerText.appendText("Server Status: " + status.getNewStatus().toString() + "\n");
      });
      server.startAsync();

      // tracking services attached to this local server
      services = server.getServices();

      // update buttons on layout
      btnStartServer.setDisable(true);
      btnStopServer.setDisable(false);
      btnStartService.setDisable(false);

      // stopping server
    } else if (eventTarget.equals(btnStopServer)) {
      terminate();
      btnStartServer.setDisable(false);
      btnStopServer.setDisable(true);
      btnStartService.setDisable(true);
      btnStopService.setDisable(true);
    }
  }

  /**
   * Gets the selected item from the combo box and creates a Local Service to add to the Local Server. 
   * <p>
   * Only creates a Local Service that is not currently  running on the Local Server.
   * 
   * @param event the event that fired this action
   * @throws URISyntaxException if URL passed to Local Service is incorrect
   */
  @FXML
  protected void handleStartService(ActionEvent event) throws URISyntaxException {

    if (server.getStatus() == LocalServerStatus.STARTED) {
      // check that the service selected is not already running
      boolean serviceExists = false;
      String serviceSelected = serviceOptions.getSelectionModel().getSelectedItem();
      for (String runningService : runningServices.getItems()) {
        if (runningService.startsWith(serviceSelected)) {
          serviceExists = true;
        }
      }

      // start service if not currently running on the Local Server
      if (!serviceExists) {
        String serviceUrl = "";
        LocalService localService = null;
        switch (serviceSelected) {
          case "Map Service":
            serviceUrl = Paths.get(getClass().getResource("/PointsofInterest.mpk").toURI()).toString();
            localService = new LocalMapService(serviceUrl);
            break;
          case "Feature Service":
            serviceUrl = Paths.get(getClass().getResource("/PointsofInterest.mpk").toURI()).toString();
            localService = new LocalFeatureService(serviceUrl);
            break;
          case "Geoprocessing Service":
            serviceUrl = Paths.get(getClass().getResource("/MessageInABottle.gpk").toURI()).toString();
            localService = new LocalGeoprocessingService(serviceUrl);
        }

        startLocalService(localService, serviceSelected);

      } else {
        // if service is currenlt running on server warn user
        Platform.runLater(() -> {
          Alert dialog = new Alert(AlertType.INFORMATION);
          dialog.setHeaderText("Service Running");
          dialog.setContentText(serviceSelected + " has already been started.");
          dialog.showAndWait();
        });
      }
    }
  }

  /**
   * Starts a Local Service.
   * <p>
   * The status of the Local Service is displayed in the center text area of the application.
   * The URL of the Local Service is added to the list of running services.
   * 
   * @param localService Local Service to start.
   * @param serviceName Name of Local Service. 
   */
  private void startLocalService(LocalService localService, String serviceName) {
    localService.addStatusChangedListener(status -> {
      centerText.appendText(serviceName + " Status: " + status.getNewStatus().toString() + "\n");
      if (status.getNewStatus() == LocalServerStatus.STARTED) {
        Platform.runLater(() -> runningServices.getItems().add(serviceName + " URL ->  " + localService.getUrl()));

      } else if (isServerStopping && status.getNewStatus() == LocalServerStatus.STOPPED) {
        int servicesStarted = services.size();
        for (LocalService service : services) {
          if (service.getStatus() == LocalServerStatus.STOPPED) {
            servicesStarted--;
          }
        }
        if (servicesStarted == 0) {
          System.out.println("Service Stopped");
          stopServer();
        }
      }
    });
    localService.startAsync();
  }

  /**
   * Gets the Local Service that is selected from the list of running services and stops it. 
   * 
   * @param event the event that fired this action
   */
  @FXML
  protected void handleStopService(ActionEvent event) {

    int serviceIndex = runningServices.getSelectionModel().getSelectedIndex();
    services.get(serviceIndex).stopAsync();
    runningServices.getItems().remove(serviceIndex);
  }

  /**
   * Enables the URL and Stop Service button when a service is selected.
   */
  @FXML
  protected void enableServiceButtons() {

    int listIndex = runningServices.getSelectionModel().getSelectedIndex();
    if (listIndex != -1) {
      btnStopService.setDisable(false);
      btnURL.setDisable(false);
    } else {
      btnStopService.setDisable(true);
      btnURL.setDisable(true);
    }
  }

  /**
   * Opens a browser to the Local Service that is selected in the list of running services. 
   * 
   * @param event event that fired this action
   */
  @FXML
  protected void handleURL(ActionEvent event) {
    String url = runningServices.getSelectionModel().getSelectedItem().split(">")[1].trim();
    hostServices.showDocument(url);
  }

  /**
   * Stops any Local Services that are currently running and then stops the Local Server. 
   */
  void terminate() {
    isServerStopping = true;
    stopServicesAndServer();
  }

  /**
   * Waits for all services that are running on the Local Server to completely stop and then stops the Local Server.
   */
  private void stopServicesAndServer() {

    if (services != null && services.size() > 0) {
      // stop any services that have been started
      for (LocalService service : services) {
        if (service.getStatus() == LocalServerStatus.STARTED) {

          ListenableFuture<Void> stop = service.stopAsync();
          // stop server once all services have stopped
          //                    stop.addDoneListener(() -> {
          //                      int servicesStarted = services.size();
          //                      for (LocalService s : services) {
          //                        if (s.getStatus() == LocalServerStatus.STOPPED) {
          //                          servicesStarted--;
          //                        }
          //                      }
          //                      if (servicesStarted == 0) {
          //                        stopServer();
          //                      }
          //                    });
        }
      }
      runningServices.getItems().clear();
    } else {
      stopServer();
    }
  }

  /**
   * Stops the Local Server if it is running.
   */
  private void stopServer() {

    if (server != null && server.getStatus() == LocalServerStatus.STARTED) {
      server.stopAsync();
    }
  }

  /**
   * Allows access to the Host Services of the main JavaFX application. 
   * 
   * @param hostServices Hosted Services from main JavaFX application
   */
  public void setHostServices(HostServices hostServices) {
    this.hostServices = hostServices;
  }
}
