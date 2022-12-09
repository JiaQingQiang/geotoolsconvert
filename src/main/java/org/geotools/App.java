package org.geotools;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

public class App 
{
    public static double[] convert(double lon, double lat)
            throws FactoryException, MismatchedDimensionException, TransformException {
        // 传入原始的经纬度坐标
        Coordinate sourceCoord = new Coordinate(lon, lat);
        GeometryFactory geoFactory = new GeometryFactory();
        Point sourcePoint = geoFactory.createPoint(sourceCoord);

        // 这里是以OGC WKT形式定义的是World Mercator投影，网页地图一般使用该投影
        final String strWKTMercator = "PROJCS[\"Transverse_Mercator\","
                + "GEOGCS[\"GCS_00\","
                + "DATUM[\"D_00\","
                + "SPHEROID[\"Krassovsky\",6378245,298.257223563]],"
                + "PRIMEM[\"Greenwich\",0],"
                + "UNIT[\"Degree\",0.017453292519943295]],"
                + "PROJECTION[\"Transverse_Mercator\"],"
                + "PARAMETER[\"False_Easting\",-47.868],"
                + "PARAMETER[\"False_Northing\",-136.531],"
                + "PARAMETER[\"Central_Meridian\",121.4666666667],"
                + "PARAMETER[\"latitude_of_origin\",31.23418348890005],"
                + "PARAMETER[\"scale_factor\",1],"
                + "UNIT[\"Meter\",1]]";;
        CoordinateReferenceSystem mercatroCRS = CRS.parseWKT(strWKTMercator);
        // 做投影转换，将WCG84坐标转换成世界墨卡托投影转
        MathTransform transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, mercatroCRS,true);
        Point targetPoint = (Point) JTS.transform(sourcePoint, transform);

        // 返回转换以后的X和Y坐标
        return new double[]{targetPoint.getX(), targetPoint.getY()};
    }
    public static void main( String[] args ) throws FactoryException, TransformException {
        double longitude = 121.08201318;
        double latitude =  31.16144854;
        double[] coordinate = convert(longitude, latitude);
        System.out.println("X: " + coordinate[0] + ", Y: " + coordinate[1]);
    }
}
