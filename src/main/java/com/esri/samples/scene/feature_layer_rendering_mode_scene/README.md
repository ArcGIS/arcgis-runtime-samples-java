<h1>Feature Layer Rendering Mode (Scene)</h1>

<p>Render features statically or dynamically in 3D.</p>

<p><img src="FeatureLayerRenderingModeScene.gif"/></p>

<h2>How it works</h2>

<p>To change <code>FeatureLayer.RenderingMode</code> using <code>LoadSettings</code>:</p>

<ol>
    <li>Create a <code>ArcGISScene</code>.</li>
    <li>Set preferred rendering mode to scene, <code>sceneBottom.getLoadSettings().setPreferredPointFeatureRenderingMode(FeatureLayer.RenderingMode.DYNAMIC)</code>.
      <ol>
        <li>Can set preferred rendering mode for <code>Points</code>, <code>Polylines</code>, or <code>Polygons</code>.</li>
        <li><code>Multipoint</code> preferred rendering mode is the same as point.</li>
      </ol>
    </li>
    <li>Set scene to <code>SceneView</code>, <code>sceneViewBottom.setArcGISScene(sceneBottom)</code>.</li>
    <li>Create a <code>ServiceFeatureTable</code> from a point service, <code>new ServiceFeatureTable("http://sampleserver6.arcgisonline.com/arcgis/rest/services/Energy/Geology/FeatureServer/0");</code>.</li>
    <li>Create <code>FeatureLayer</code> from table, <code>new FeatureLayer(poinServiceFeatureTable)</code>.</li>
    <li>Add layer to scene, <code>sceneBottom.getOperationalLayers().add(pointFeatureLayer.copy())</code>
      <ol>
        <li>Now the point layer will be rendered dynamically to scene view.</li>
      </ol>
    </li>
</ol>

<h2>Relevant API</h2>

<ul>
    <li>ArcGISScene</li>
    <li>Camera</li>
    <li>FeatureLayer</li>
    <li>FeatureLayer.RenderingMode</li>
    <li>LoadSettings</li>
    <li>Point</li>
    <li>Polyline</li>
    <li>Polygon</li>
    <li>ServiceFeatureTable</li>
</ul>


