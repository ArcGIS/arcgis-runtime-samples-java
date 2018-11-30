<h1>Create Geometries</h2>

<p>Create simple geometry types.</p>

<p><img src="CreateGeometries.png"/></p>

<h2>How it works</h2>
<p>To create different geometries and show them as graphics:</p>
<ol>
  <li>Use the constructors for the various simple <code>Geometry</code> types including <code>Point</code>, 
  <code>Polyline</code>, <code>Multipoint</code>, <code>Polygon</code>, and <code>Envelope</code>. Geometries made 
  of multiple points usually take a <code>PointCollection</code> as an argument.</li>
  <li>To display the geometry, create a <code>Graphic</code> passing in the geometry, and a <code>Symbol</code> 
  appropriate for the geometry type.</li>
  <li>Add the <code>Graphic</code> to a <code>GraphicsOverlay</code> and add the overlay to the 
  <code>MapView</code>.</li>
</ol>

<h2>Relevant API</h2>  
<ul>
<li>Envelope</li>
<li>Graphic</li>
<li>Multipoint</li>
<li>Point</li>
<li>PointCollection</li>
<li>Polygon</li>
<li>Polyline</li>
</ul>