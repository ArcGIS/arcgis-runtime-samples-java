<h1>Display Drawing Status</h1>

<p>Determine if a layer is done drawing.</p>

<p><img src="DisplayDrawingStatus.png"/></p>

<h2>How to use the sample</h2>

<p>The progress bar in the top left displays the drawing status of the map view.</p>

<h2>How it works</h2>

<p>To use the <code>MapView</code>'s <code>DrawStatus</code>:</p>

<ol>
    <li>Create an <code>ArcGISMap</code>. </li>
    <li>Set the map to the view <code>MapView</code>, <code>MapView.setMap()</code>. </li>
    <li>Add <code>MapView.addDrawStatusChangedListener()</code> block and listen when the <code>MapView.DrawStatus</code> changes.</li>
</ol>

<h2>Relevant API</h2>

<ul>
    <li>ArcGISMap</li>
    <li>Basemap</li>
    <li>DrawStatus </li>
    <li>DrawStatusChangedEvent</li>
    <li>Envelope</li>
    <li>FeatureLayer</li>
    <li>MapView</li>
    <li>Point</li>
</ul>
