# freemarker basic

### 取值指令
* 常用`${key}`语法进行取值
* 对于null、不存在的对象取值`${key!}`，`!`表示值不存在或为null时不输出，否则会报错
* 取包装对象的值，通过`.`语法，`${user.name}`
* 取值的时候支持进行计算、赋值
* Date类型格式`${date?string('yyyy-MM-dd HH:mm:ss')}`，`?string()表示调用freemark内置函数，将日期格式化`
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
	<#case  >
		...
	<#break>
	<#case value>
		...
	<#break>
	<#default>
		...
</#switch>
```