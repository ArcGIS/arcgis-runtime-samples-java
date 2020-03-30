# Open map (URL)

Display a web map.

![Image of open map URL](OpenMapURL.png)

## Use case

For displaying web maps stored on ArcGISOnline (e.g. soil survey results, as demonstrated in this sample).

## How to use the sample

A web map can be selected from the drop-down list. On selection the web map displays in the map view.

## How it works

1. Create a `Portal`.
2. Create a `PortalItem` using the Portal and the web map ID: `new PortalItem(portal, ID)`.
3. Create a `ArcGISMap` using the portal item.
4. Set the map to the `MapView`.

## Relevant API

* ArcGISMap
* MapView
* Portal
* PortalItem

## Tags

portal item, web map
