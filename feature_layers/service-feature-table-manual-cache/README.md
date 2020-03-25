# Service feature table (manual cache)

Display a feature layer from a service using the **manual cache** feature request mode.

![Image of service feature table manual cache](ServiceFeatureTableManualCache.png)

## Use case

`ServiceFeatureTable` supports three request modes, which define how features are requested from the service and stored in the local table. The feature request modes have different performance characteristics. Use **manual cache** in scenarios where you want to explicitly control requests for features. See [Table performance concepts](https://developers.arcgis.com/net/latest/wpf/guide/layers.htm#ESRI_SECTION1_40F10593308A4718971C9A8F5FB9EC7D) to learn more.

## How to use the sample

Run the sample and pan and zoom around the map. No features will be requested and displayed automatically. Press the "Request Cache" button to have features and a count of features display.

Note: Maximum of Features returned is set to 1000.

## How it works

1. Set the `ServiceFeatureTable.FeatureRequestMode` property of the service feature table to `MANUAL_CACHE` before the table is loaded.
2. Load the table.
3. Call `populateFromServiceAsync()` on the table to request features.

## Relevant API

* FeatureLayer
* ServiceFeatureTable.FeatureRequestMode
* ServiceFeatureTable

## Additional information

In **manual cache** mode, features are never automatically populated from the service. All features are loaded manually using calls to `PopulateFromServiceAsync`.

## Tags

cache, feature request mode, performance
