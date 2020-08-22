# ES-Player by cmf&&lss

### API

接口已在ApiPost中建立完毕，路径可再检查，请求参数自行补充。

## update

重新划分了层次文件，按照分工负责的接口，各自于自己的package完成模块编写。对于修改过的文件结构，各自只需关心各自的Package和共用部分的Domain，以及对应Dao-Mapper.xml,记得在mybatis-config.xml中添加映射。

```
com.player.es
	cmf // cmf负责的模块
		Controller
		Dao
		Domain
		Service
	lss // lss负责的模块
		Controller
		Dao
		Domain
		Service
	Config  
	Domain // 共用的实体类
	shiro
	Utils 
//resources
	mybatis-config.xml
	mappper
		cmfMapper
			xxxMapper.xml
		lssMapper
			xxMapper.xml
```

