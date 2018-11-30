<h1>Change Atmosphere Effect</h1>

<p>Change the appearance of the atmosphere in a scene.</p>

<p><img src="ChangeAtmosphereEffect.gif" /></p>

<h2>How to use the sample</h2>

<p>Select one of the three available atmosphere effects. The sky will change to display the selected atmosphere effect. </p>

<h2>How it works</h2>

<p>To change the atmosphere effect:</p>

<ol>
<li>Create an <code>ArcGISScene</code> and display it in a <code>SceneView</code>.</li>

<li>Change the atmosphere effect with <code>sceneView.setAtmosphereEffect(atmosphereEffect)</code>.</li>
</ol>

<h2>Relevant API</h2>

<ul>
<li><code>ArcGISScene</code></li>

<li><code>AtmosphereEffect</code></li>

<li><code>SceneView</code></li>

</ul>

<h2>Additional Information</h2>
There are three atmosphere effect options:

<ul>
<li><strong> Realistic</strong> - Atmosphere effect applied to both the sky and the surface as viewed from above.</li> 
<li><strong> Horizon only</strong> - Atmosphere effect applied to the sky (horizon) only.</li>
<li><strong> None</strong> - No atmosphere effect. The sky is rendered black with a starfield consisting of randomly placed white dots.</li>
</ul>

<h2>Tags</h2>

<p>3D, AtmosphereEffect, Scene </p>

