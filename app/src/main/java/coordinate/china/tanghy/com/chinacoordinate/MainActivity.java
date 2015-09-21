package coordinate.china.tanghy.com.chinacoordinate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;

import coordinate.china.tanghy.com.coordinatetransform.coord.Latlon;
import coordinate.china.tanghy.com.coordinatetransform.utils.TransformUtils;


public class MainActivity extends ActionBarActivity {

    MapView mMapView;
    GraphicsLayer graphicsLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView)findViewById(R.id.map);
//
//        ArcGISTiledMapServiceLayer tiledMapServiceLayer = new ArcGISTiledMapServiceLayer(getString(R.string.layerUrl));
//
//        mMapView.addLayer(tiledMapServiceLayer);

        graphicsLayer = new GraphicsLayer();
        Point point = GeometryEngine.project(116.403883,39.914884, SpatialReference.create(102100));
        Graphic graphic = new Graphic(point,new SimpleMarkerSymbol(Color.RED, 15, SimpleMarkerSymbol.STYLE.CIRCLE));

        Latlon latlon = TransformUtils.transform(39.914884,116.403883);

        Point point1 = new Point(latlon.getLongitude(),latlon.getLatitude());
        Graphic graphic1 = new Graphic(point1,new SimpleMarkerSymbol(Color.BLUE, 15, SimpleMarkerSymbol.STYLE.CIRCLE));

        graphicsLayer.addGraphic(graphic);
        graphicsLayer.addGraphic(graphic1);
        mMapView.addLayer(graphicsLayer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
