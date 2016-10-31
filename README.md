# BuildVariantsSample
###使用BuildVariants构建测试版本与正式版本
####在APP开发过程中一般至少会存在两个环境，正式环境和测试环境，随着后期版本的迭代，经常需要在两个环境中来回切换。如果手动在这两个环境中来回切换，可能我们需要更改，百度或友盟的key，更改接口请求的地址，端口等等。。。可能由于哪个地方没改到造成线上版本的名称或是统计定位出现问题，使用BuildVariants则可以避免这个问题，在打包发布时也更加方便。
#####1.设置productFlavors，构建两个差异版本，appfortest（测试版本），appforrelease（发布版本）
```
productFlavors {
        appfortest {

        }
        appforrelease{

        }

    }
```

#####2.动态修改meta标签里的value值
在manifest里面新建一个测试标签,value值需要用${ }的方式表示，以便可以动态设置value的值
```
  <meta-data
            android:name="TEST_KEY"
            android:value="${TEST_KEY_VALUE}" />
```
然后在productFlavors里将两个版本分别配置上对应的值,over
```
productFlavors {

        appfortest {
            manifestPlaceholders=[TEST_KEY_VALUE:"appfortest"]
        }

        appforrelease{
            manifestPlaceholders=[TEST_KEY_VALUE:"appforrelease"]
        }

    }
```

#####3.动态修改接口请求的IP和端口,添加buildConfigField API_EVN
```
 productFlavors {

        appfortest {
            manifestPlaceholders=[TEST_KEY_VALUE:"appfortest"]
            buildConfigField "String" ,"API_EVN", "\"APITEST\""
        }

        appforrelease{
            manifestPlaceholders=[TEST_KEY_VALUE:"appforrelease"]
            buildConfigField "String" ,"API_EVN" ,"\"APIRELEASE\""
        }

    }
```
在constant类中添加static代码块
```
 static {
        switch (BuildConfig.API_EVN) {
            case "APITEST":
                APP_HOST = "192.168.152.1";
                APP_PORT = ":8080";
                break;

            case "APIRELEASE":
                APP_HOST = "www.release.com";
                APP_PORT = ":80";
                break;
        }
    }
```

#####4.定义打包生成的文件名
```
 applicationVariants.all {
            variant ->
                variant.outputs.each { output ->
                    output.outputFile = new File(
                            output.outputFile.parent,
                            output.outputFile.name.replace(output.outputFile.name, variant.productFlavors[0].name + "${variant.versionName}.apk"))
                }
        }
```

#####最后测试meta标签和其它配置在不同版本打包时是否生效
```
  try {
                    ApplicationInfo appInfo = this.getPackageManager()
                            .getApplicationInfo(getPackageName(),
                                    PackageManager.GET_META_DATA);
                    String msg = appInfo.metaData.getString("TEST_KEY");
                    Toast.makeText(this, msg + "--------" + Constant.APP_HOST + Constant.APP_PORT, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
```
#####OK