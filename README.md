# freemarker basic

### 取值指令
* 常用`${key}`语法进行取值
* 对于null、不存在的对象取值`${key!}`，`!`表示值不存在或为null时不输出，否则会报错
* 取包装对象的值，通过`.`语法，`${user.name}`
* 取值的时候支持进行计算、赋值
* Date类型格式`${date?string('yyyy-MM-dd HH:mm:ss')}`，`?string()`表示调用freemark内置函数，将日期格式化
* 如何转义HTML内容：`${key?html}`

### 赋值
* `assign`:`<#assign x=100 />`

### 分支结构
* `if`语句
```
<!-- 判断userList是否非null -->
<#if userList??>
 ...
</#if>

<!-- 或者写成这样 -->
<#if userList?exists>
 ...
</#if>
```
通用的if语句
```
<#if condition>
 ...
<#elseif condition>
 ...
<#else>
 ...
</#if>
```

* `&&`与、`||`或、`!`非
```
<#if (a==1||b==2)&&c==3>
 ...
</#if>
```

* `switch`语句
```
<!-- 支持string类型 -->
<#switch var>
	<#case value>
		...
	<#break>
	<#case value>
		...
	<#break>
	<#default>
		...
</#switch>
```

### 内建函数
```
<#assign a='Hello' />
<#assign b='World' />
```
* 字符串拼接：`${a+b}`
* 字符串长度：`${(a+b)?length}`
* 字符串截取：`${(a+b)?substring(1,4)}`
* 字符串转换成小写：`${(a+b)?lower_case}`
* 字符串转换成大写：`${(a+b)?upper_case}`
* 字符串查找：`${(a+b)?index_of('o')}`
* 字符串查找：`${(a+b)?last_index_of('o')}`
* 字符串替换：`${(a+b)?replace('o','x')}`


# freemarker advanced

### 自定义函数
```
<#assign myList=[2,1,5,4,3,6,7,4] />
<#list mySort(myList) as item>
	${item},
</#list>
<!-- mySort是自定义排序函数 -->
```
后台代码定义如下
```Java
//controller中返回mySort
model.addAttribute("mySort", new FreeMarkerSort());

//FreeMarkerSort类定义如下
public class FreeMarkerSort implements TemplateMethodModelEx {
	
	public Object exec(List arguments) throws TemplateModelException {
		// 第一个参数
		SimpleSequence simpleSequence = (SimpleSequence) arguments.get(0);
		List<BigDecimal> list = simpleSequence.toList();

		Collections.sort(list, new Comparator<BigDecimal>() {
			public int compare(BigDecimal o1, BigDecimal o2) {
				return o1.intValue() - o2.intValue();
			}
		});
		return list;
	}
	
}
```
freemarker自带了List排序函数
```
<!-- 升序 -->
<#list myList?sort as item>
	${item},
</#list>
<!-- 降序 -->
<#list myList?sort?reverse as item>
	${item},
</#list>
```

### 自定义指令
```
<!-- 自定义指令<@role> -->
<!--
1、freemarker内置指令以<#开头，自定义指令以<@开头
2、分号前面的user='123456' role='admin'中间以空格分开，以key-value的形式存在
3、分号后面result1和result2为返回值，以","隔开
-->
<@role user='123456' role='admin';result1, result2>
	<#if result1>
		我的角色是：admin
	</#if>
	<br/>
	我的权限是：
	<#list result2 as item>
		${item}
	</#list>
	<br/>
</@role>
```
自定义指令需要在spring的配置文件中进行相关配置
```xml
<bean id="freemarkerConfig" class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer" >
	...
	<property name="freemarkerVariables">
		<map>
			<!-- key为自定义标签名，value-ref为实现类 -->
			<entry key="role" value-ref="roleDirectiveModel"/>
		</map>
	</property>
</bean>
```
RoleDirectiveModel类实现如下
```Java
@Service
public class RoleDirectiveModel implements TemplateDirectiveModel {

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {

		System.out.println("RoleDirectiveModel.execute");

		TemplateScalarModel user = (TemplateScalarModel) params.get("user");
		TemplateScalarModel role = (TemplateScalarModel) params.get("role");

		if ("123456".equals(user.getAsString()) && "admin".equals(role.getAsString())) {
			loopVars[0] = TemplateBooleanModel.TRUE;
		}

		List<String> otherRights = new ArrayList<String>();
		otherRights.add("add");
		otherRights.add("delete");
		otherRights.add("update");
		loopVars[1] = new SimpleSequence(otherRights);

		body.render(env.getOut());
	}

}
```

### 宏macro
```
<!-- 定义，参数可以有默认值，但是没有默认值的参数必须放在有默认值的参数之前 -->
<#macro test param1 param2 param3="freemarker">
	macro : param1 is ${param1}, param2 is ${param2}, param3 is ${param3}
</#macro>
<!-- 调用，调用时必须给所有没有默认值的参数赋值 -->
<@test param1="Hello" param2="world" />
<br/>

<!-- 参数个数不确定的macro，paramExt...以Map的形式存在 -->
<#macro test param1 param2 paramExt...>
	macro : param1 is ${param1}, param2 is ${param2}, param3 is ${paramExt['param3']}
</#macro>
<!-- 调用 -->
<@test param1="Hello" param2="world" param3="freemarker" />
```

### macro + nested
```
<!-- 定义 -->
<#macro test>
	nested : <#nested />
</#macro>
<!-- 调用 -->
<@test>i am nested</@test>
<br/>

<!--定义，有参数的nested -->
<!--
nested中定义了两个参数，param1和默认值，nested1中的param1实际上是由macro中的param1传值
-->
<#macro test param1>
	nested: <#nested param1, "default value" />
</#macro>
<!-- 调用 -->
<@test param1 = "hello"; nestedParam1, nestedParam2>
	${nestedParam1} ${nestedParam2}
</@test>
```

### 函数
```
<!-- 定义 -->
<#function doAdd param1 param2>
	<#return param1+param2 />
</#function>
<!-- 调用 -->
add is ${doAdd(100, 200)}
```