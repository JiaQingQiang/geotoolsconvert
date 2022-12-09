# geotoolsconvert
基于geotools的倾斜摄影大地坐标转自定义投影坐标(经纬度转xyz)springboot项目  
此项目适用于经纬度转换自定义坐标系。  
1.引入pom.xml中的相关geotools配置  
2.在\src\main\java\org\geotools\App.java中  
修改自定义投影坐标系转换参数strWKTMercator。具体的转换一般在cc生产后的模型文件夹metadata.xml中，如若没有可询问模型生产人员具体详情。
