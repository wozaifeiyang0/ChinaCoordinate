package coordinate.china.tanghy.com.coordinatetransform.utils;

import coordinate.china.tanghy.com.coordinatetransform.coord.Latlon;

/**
 * Created by Tanghy000 on 2015/9/17.
 * 坐标转换工具，通过传入WGS84坐标系的坐标转换成火星坐标系（中国官方指定坐标系）
 */
public class TransformUtils {

    //圆周率
    private final static double pi = 3.14159265358979324;
    private final static double a = 6378245.0;
    private final static double ee = 0.00669342162296594323;

    /**
     * WGS84坐标转换火星坐标
     * @param wgsLat
     * @param wgsLon
     * @return
     */
    public static Latlon transform(double wgsLat,double wgsLon){

        Latlon latlon = new Latlon();
        //判定坐标是否在中国范围内
        if (outofChina(wgsLat, wgsLon)) {

            latlon.setLatitude(wgsLat);
            latlon.setLongitude(wgsLon);
        }

        double dLat = transformLat(wgsLon - 105.0, wgsLat - 35.0);
        double dLon = transformLon(wgsLon - 105.0, wgsLat - 35.0);
        double radLat = wgsLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = wgsLat + dLat;
        double mgLon = wgsLon + dLon;

        latlon.setLongitude(mgLon);
        latlon.setLatitude(mgLat);

        return latlon;

    }

    /**
     * WGS84坐标转换火星坐标
     * @param wgsLatlon
     * @return
     */
    public static Latlon transform(Latlon wgsLatlon){
        double wgsLat = wgsLatlon.getLatitude();
        double wgsLon = wgsLatlon.getLongitude();
        return transform(wgsLat,wgsLon);
    }



    /**
     * 判定坐标是否在中国范围内
     * @param lat 维度
     * @param lon 经度
     * @return
     */
    private static boolean outofChina(double lat ,double lon){

        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;

    }


    private static double transformLat(double x, double y)
    {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y)
    {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }


}
