# ES-Player by cmf&&lss
### profit
se.silence.pink
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



## 效果预览

### 登陆

![登录](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/%E7%99%BB%E5%BD%95.png)



### 首页

![image-20200918183638515](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/%E9%A6%96%E9%A1%B5.png)



### 后台管理

![image-20200918185904489](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86-%E8%B5%9B%E4%BA%8B%E7%AE%A1%E7%90%86.png)



### 数据分析

![image-20200918190040963](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/ans1.png)

![image-20200918190124073](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/ans2.png)

![image-20200918190153735](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/ans3.png)



### 数据对比

![image-20200918190238019](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/cmp1.png)

![image-20200918190313820](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/cmp2.png)

![image-20200918190334802](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/cmp3.png)



### 赛程页面

![image-20200918190800231](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/%E8%B5%9B%E4%BA%8B%E4%B8%AD%E5%BF%83.png)



### 球队页面

![image-20200918190419102](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/tm1.png)

![image-20200918190445556](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/tm2.png)

![image-20200918190517611](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/tm3.png)



### 球员页面

![image-20200918190555382](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/pl1.png)

![image-20200918190618692](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/pl2.png)



### 赛事页面

![image-20200918190642796](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/mt1.png)

![image-20200918190715691](https://es-1301702299.cos.ap-nanjing.myqcloud.com/es/mt2.png)
