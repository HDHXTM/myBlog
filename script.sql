create database myBlog;
use myBlog;

create table if not exists role
(
    id   int auto_increment
        primary key,
    name varchar(10) null
);

create table if not exists tag
(
    id   int auto_increment
        primary key,
    name varchar(30) null
);

create table if not exists type
(
    id   int auto_increment
        primary key,
    name varchar(30) null
);

create table if not exists user
(
    id       int auto_increment
        primary key,
    logName  varchar(20) null,
    roleId   int         null,
    nickName varchar(30) null,
    pwd      char(80)    null,
    img      char(255)   null,
    email    char(50)    null,
    constraint logName
        unique (logName),
    constraint user_email_uindex
        unique (email),
    constraint user_role_id_fk
        foreign key (roleId) references role (id)
            on update cascade on delete set null
);

create table if not exists blog
(
    id         int auto_increment
        primary key,
    title      varchar(255) null,
    content    text         null,
    picture    char(255)    null,
    createTime datetime     null,
    updateTime datetime     null,
    type       int          null,
    author     int          null,
    constraint blog_type_id_fk
        foreign key (type) references type (id)
            on update cascade on delete set null,
    constraint blog_user_id_fk
        foreign key (author) references user (id)
            on update cascade on delete cascade
);

create table if not exists blog_tag
(
    blogId int null,
    tagId  int null,
    constraint blog_tag_blog_id_fk
        foreign key (blogId) references blog (id)
            on update cascade on delete cascade,
    constraint blog_tag_tag_id_fk
        foreign key (tagId) references tag (id)
            on update cascade on delete cascade
);

create table if not exists comment
(
    id         int auto_increment
        primary key,
    content    text     null,
    createTime datetime null,
    userId     int      null,
    blogId     int      null,
    parentId   int      null,
    toUserId   int      null,
    constraint comment_blog_id_fk
        foreign key (blogId) references blog (id)
            on update cascade on delete cascade,
    constraint comment_comment_id_fk
        foreign key (parentId) references comment (id)
            on update cascade on delete cascade,
    constraint comment_user_id_fk
        foreign key (userId) references user (id)
            on update cascade on delete cascade,
    constraint comment_user_id_fk_2
        foreign key (toUserId) references user (id)
            on update cascade on delete cascade
);

INSERT INTO myblog.role (id, name) VALUES (1, 'admin');
INSERT INTO myblog.role (id, name) VALUES (2, 'user');

INSERT INTO myblog.tag (id, name) VALUES (1, 'java');
INSERT INTO myblog.tag (id, name) VALUES (2, 'c');
INSERT INTO myblog.tag (id, name) VALUES (3, 'python');
INSERT INTO myblog.tag (id, name) VALUES (4, 'springboot');
INSERT INTO myblog.tag (id, name) VALUES (5, '前端');
INSERT INTO myblog.tag (id, name) VALUES (7, '数据库');
INSERT INTO myblog.tag (id, name) VALUES (8, '其他');

INSERT INTO myblog.type (id, name) VALUES (1, '学习交流');
INSERT INTO myblog.type (id, name) VALUES (2, '福利活动');
INSERT INTO myblog.type (id, name) VALUES (3, '教程');
INSERT INTO myblog.type (id, name) VALUES (4, '动漫');
INSERT INTO myblog.type (id, name) VALUES (5, '聊天灌水');

INSERT INTO myblog.user (id, logName, roleId, nickName, pwd, img, email) VALUES (1, 'lbw', 1, 'lbw', '$2a$10$q.Fxwr0/i.kaPsjBRhRTlOWK4BEmaAcmnIoSfszBTrUS4mbm9DVpm', '\\userImg\\e1535157badf40fa8d2c046a3d21d674_1.gif', '1@qq.com');
INSERT INTO myblog.user (id, logName, roleId, nickName, pwd, img, email) VALUES (2, 'cxk', 2, 'cxk', '$2a$10$q.Fxwr0/i.kaPsjBRhRTlOWK4BEmaAcmnIoSfszBTrUS4mbm9DVpm', '\\userImg\\ea267e488aeb4d43a10af31518b18ca8_2.gif', '2@qq.com');
INSERT INTO myblog.user (id, logName, roleId, nickName, pwd, img, email) VALUES (3, 'mbg', 2, '马保国', '$2a$10$q.Fxwr0/i.kaPsjBRhRTlOWK4BEmaAcmnIoSfszBTrUS4mbm9DVpm', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584367404804&di=070c78aac95428c480b480a87b534e96&imgtype=0&src=http%3A%2F%2Fbbs.cnlinfo.net%2Fup%2Ftou%2F150611164743.jpg', '3@qq.com');
INSERT INTO myblog.user (id, logName, roleId, nickName, pwd, img, email) VALUES (14, '1', 2, '1', '$2a$10$UlSKMgNnIsnmMPWwVx7ileyzJgERT6Shzg6oZk9aOBWrxZfIaCdHO', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584367404804&di=070c78aac95428c480b480a87b534e96&imgtype=0&src=http%3A%2F%2Fbbs.cnlinfo.net%2Fup%2Ftou%2F150611164743.jpg', '24@qq.com');

INSERT INTO myblog.blog (id, title, content, picture, createTime, updateTime, type, author) VALUES (1, 'maven中静态资源的过滤', '# maven中静态资源的过滤

pom.xml文件中加入下面配置

### 可以过滤java和resources文件夹里面所有的的.properties和.xml文件
**directory：指定资源所在的目录，目录的路径是相对于pom.xml文件的
includes：指定要包含哪些文件**
**filtering标签中：false表示不过滤，true表示过滤**

```java
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
```
', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584367888190&di=7855ff350e759b4b3de8c614978673b4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201612%2F07%2F20161207154722_Cmce5.thumb.400_0.gif', '2020-03-12 08:25:47', '2020-03-13 11:56:19', 1, 1);
INSERT INTO myblog.blog (id, title, content, picture, createTime, updateTime, type, author) VALUES (2, '使用System.out.format()格式化输出', '#### JDK5.0允许java像C语言那样直接用printf()方法来格式化输出
####  System.out.format()功能与printf()一样，可以使用%d,%f等参数。
使用System.out.format()完成**左对齐，补0，千位分隔符，小数点位数，本地化表达**
```java
public class TestNumber {

    public static void main(String[] args) {
        int year = 2020;
        //左对齐，补0，千位分隔符，小数点位数，本地化表达

      //直接打印数字
        System.out.println(year);

        //直接打印数字
        System.out.format("%d%n",year);
        //总长度是8,默认右对齐
        System.out.format("%8d%n",year);
        //总长度是8,左对齐
        System.out.format("%-8d%n",year);
        //总长度是8,不够补0
        System.out.format("%08d%n",year);
        //千位分隔符
        System.out.format("%,8d%n",year*10000);

        //保留5位小数
        System.out.format("%.5f%n",Math.PI);

        //不同国家的千位分隔符
        System.out.format(Locale.FRANCE,"%,.2f%n",Math.PI*10000);
        System.out.format(Locale.US,"%,.2f%n",Math.PI*10000);
        System.out.format(Locale.UK,"%,.2f%n",Math.PI*10000);

    }
}
```
输出结果：

```java
2020
2020
    2020
2020
00002020
20,200,000
3.14159
31?415,93
31,415.93
31,415.93
```
', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584368822685&di=19855856bbd158b52926a49b51e876c0&imgtype=0&src=http%3A%2F%2Fwx2.sinaimg.cn%2Fcrop.0.4.1280.711%2F70745653ly1fvuflwe10zj20zk0k0n06.jpg', '2019-03-12 13:42:14', '2019-03-13 12:00:08', 1, 1);
INSERT INTO myblog.blog (id, title, content, picture, createTime, updateTime, type, author) VALUES (3, 'Springboot中PageHelper 分页查询使用方法', '### 一：导入依赖

```java
<dependency>
	<groupId>com.github.pagehelper</groupId>
	<artifactId>pagehelper-spring-boot-starter</artifactId>
	<version>1.2.13</version>
</dependency>
```
### 二：配置yml文件中PageHelper的属性

```java
pagehelper:                #分页插件
  helper-dialect: mysql
  reasonable: true
  support-methods-arguments: true
  params:
```
### 三：在controller类中使用，
##### 1.在查询方法上调用PageHelper.startPage()方法，设置分页的页数和每页信息数，
##### 2.将查询出来的结果集用PageInfo的构造函数初始化为一个分页结果对象
##### 3.将分页结果对象存入model，返回前端页面
```java
@GetMapping("/types")
public String types(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){

    PageHelper.startPage(pagenum, 5);  //pagenum：页数，pagesize:每页的信息数

    List<Type> allType = typeService.getAllType(); //调用业务层查询方法

    PageInfo<Type> pageInfo = new PageInfo<>(allType); //得到分页结果对象

    model.addAttribute("pageInfo", pageInfo);  //携带分页结果信息

    return "admin/types";  //回到前端展示页面
}
```
### 四：前端展示分页（thymeleaf+semantic-ui）,这里ui用自己的就行，比如bootstrap或layui，主要是thymeleaf的使用。

```java
<table  class="ui compact celled teal table">
  <thead>
  <tr>
    <th></th>
    <th>名称</th>
    <th>操作</th>
  </tr>
  </thead>
  <tbody>
  <tr th:each="type, iterStat : ${pageInfo.list}">
    <td th:text="${iterStat.count}">1</td>
    <td th:text="${type.name}">摸鱼方法</td>
    <td>
      <a href="#" th:href="@{/admin/types/{id}/input(id=${type.id})}" class="ui mini teal basic button">编辑</a>
      <a href="#" th:href="@{/admin/types/{id}/delete(id=${type.id})}" class="ui mini red basic button">删除</a>
    </td>
  </tr>
  </tbody>
  <tfoot>
  <tr>
    <th colspan="7">
      <div class="ui mini pagination menu"  >
        <div class="item"><a th:href="@{/admin/types}">首页</a></div>
        <div class="item"><a th:href="@{/admin/types(pagenum=${pageInfo.hasPreviousPage}?${pageInfo.prePage}:1)}">上一页</a></div>
        <div class="item"><a th:href="@{/admin/types(pagenum=${pageInfo.hasNextPage}?${pageInfo.nextPage}:${pageInfo.pages})}">下一页</a></div>
        <div class="item"><a th:href="@{/admin/types(pagenum=${pageInfo.pages})}">尾页</a></div>
      </div>
      <a href="#" th:href="@{/admin/types/input}" class="ui mini right floated teal basic button">新增</a>
    </th>
  </tr>
  </tfoot>
</table>

<div class="ui segment m-inline-block">
  <p >当前第<span th:text="${pageInfo.pageNum}"></span>页，总<span th:text="${pageInfo.pages}"></span>页，共<span th:text="${pageInfo.total}"></span>条记录</p>
</div>
```
### 五：效果展示（pagesize设置为5的效果）
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200310105006168.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyODA0NzM2,size_16,color_FFFFFF,t_70)
', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584368536828&di=460fea188bd56a9f691d87e56622b891&imgtype=0&src=http%3A%2F%2Ftc.sinaimg.cn%2Fmaxwidth.800%2Ftc.service.weibo.com%2Fp3_pstatp_com%2F5ffc4f5b05b15c5642dd59cc7341cc71.jpg', '2019-03-14 17:05:58', '2020-03-21 11:45:10', 1, 1);
INSERT INTO myblog.blog (id, title, content, picture, createTime, updateTime, type, author) VALUES (4, 'thymeleaf语法及使用', '## 模板引擎

简介：模板引擎(这里特指用于Web开发的模板引擎)是为了使用户界面与业务数据(内容)分离而产生的,它可以生成特定格式的文档，用于网站的模板引擎就会生成一个标准的HTML文档。
模板引擎的思想：
![在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMuY25ibG9ncy5jb20vY25ibG9nc19jb20venltMTk5OS8xNjQ1NDE2L29fMjAwMjExMDk1OTQxMTQucG5n?x-oss-process=image/format,png)
Thymeleaf就是SpringBoot给我们推荐的一种模板引擎！

## Thymeleaf模板引擎

#### 1.使用Thymeleaf之前的步骤

 1. Thymeleaf 官网：https://www.thymeleaf.org/
 2. springboot项目直接引入依赖：
```java
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
3.非springboot项目直接引入依赖：

```java
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf</artifactId>
    <version>2.1.4</version>
</dependency>
```
  4.在thymeleaf的配置类ThymeleafProperties中我们可以发现：thymeleaf配置的默认前缀为："classpath:/templates/"，默认后缀为：".html"，只要把html页面放在这个路径下，

thymeleaf就可以帮我们自动渲染。

```java
public class ThymeleafProperties {
    private static final Charset DEFAULT_ENCODING;
    public static final String DEFAULT_PREFIX = "classpath:/templates/";
    public static final String DEFAULT_SUFFIX = ".html";
    private boolean checkTemplate = true;
    private boolean checkTemplateLocation = true;
    private String prefix = "classpath:/templates/";
    private String suffix = ".html";
    private String mode = "HTML";
...
}
```
如图为用idea创建的springboot的项目结构：将html页面放在resources/templates中即可。
![在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMuY25ibG9ncy5jb20vY25ibG9nc19jb20venltMTk5OS8xNjQ1NDE2L29fMjAwMjExMDk1OTExMTMucG5n?x-oss-process=image/format,png)
#### 2.Thymeleaf语法简单使用（th:text, th:utext, th:each）
编写一个controller实现跳转到一个html页面，通过Model对象携带数据

```java
@Controller
public class HelloController {

    @RequestMapping("/success")
    public String success(Model model){
        //存入数据
        model.addAttribute("msg","<h1>Hello</h1>");
        model.addAttribute("users", Arrays.asList("小红", "小米","小白"));
        //classpath:/templates/success.html
        return "success";
    }
}
```
success.html

```java
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>success</h1>

    <!--Thymeleaf语法：th:text就是将div中的内容设置为它指定的值-->

    <div th:text="${msg}">你好</div>
    <!--utext：会解析html，显示相应的效果-->
    <div th:utext="${msg}">你好</div>
    <!--each：遍历-->
    <h3 th:each="user:${users}" th:text="${user}"></h3>

</body>
</html>
```
通过http://localhost:8080/success路径访问到success.html页面，同时成功显示数据：![在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMuY25ibG9ncy5jb20vY25ibG9nc19jb20venltMTk5OS8xNjQ1NDE2L29fMjAwMjExMTEyOTE3MTUucG5n?x-oss-process=image/format,png)
#### 3.Thymeleaf基本语法（属性和表达式）
**Thymeleaf标准表达式**


 1. 变量表达式：**${ }**：用于前端获取后端传递的变量值



 1. 选择表达式：***{ }**：用于绑定一个对象的属性



 1. URL链接表达式：**@{ }**：用于链接



 1. 条件表达式：**三目运算符（表达式 ？值（then）：值（else））**





**Thymeleaf属性标签：**
![在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMuY25ibG9ncy5jb20vY25ibG9nc19jb20venltMTk5OS8xNjQ1NDE2L29fMjAwMjExMTEzMTAxMTYucG5n?x-oss-process=image/format,png)



', 'http://n.sinaimg.cn/sinacn20111/600/w1920h1080/20190331/0f41-huxwryw5226043.jpg', '2020-03-14 17:01:31', '2020-03-14 17:01:31', 1, 1);
INSERT INTO myblog.blog (id, title, content, picture, createTime, updateTime, type, author) VALUES (5, '利用Set集合去重', '### Set集合特点:
#### ①     一次只存一个元素,

#### ②     不能存储重复的元素

#### ③     存储顺序和取出来的顺序不一定一致不能存储重复的元素

可以利用②这一特点，完成去重的功能。
#### 一：Set集合去掉List集合中重复元素

```java
public static void main(String[] args) {

	//利用set集合 去除ArrayList集合中的重复元素
	ArrayList<String> list = new ArrayList<>();
	list.add("1");
    list.add("1");
    list.add("2");
    list.add("2");
    list.add("3");
    list.add("3");
    list.add("4");
    list.add("4");
    System.out.println("去重前的List集合："+list);

	Set<String> set = new HashSet<>();
	set.addAll(list);
	System.out.println("Set集合："+set);

	list.clear();            // 清空原有元素 放入被list去重后的元素
	list.addAll(set);
	System.out.println("去重后的List集合："+list);
}
```
运行结果：

```java
去重前的List集合：[1, 1, 2, 2, 3, 3, 4, 4]
Set集合：[1, 2, 3, 4]
去重后的List集合：[1, 2, 3, 4]
```

#### 二：Set集合去掉字符串中重复子串

```java
public static void main(String[] args) {
	String str = "aaab";
	System.out.println("字符串aaab 有非空子串a, b, aa, ab, aaa, aab, aaab，一共 7 个");

	Set<String> set = new HashSet<String>();
	for (int step = 0; step <= str.length() - 1; step++) {
		//扫描全部子串
		for (int begin = 0, end = 1 + step; end <= str.length(); begin++, end++) {
			String kid = str.substring(begin, end);   //截取字符串子串
			set.add(kid);			//将子串放入set集合，完成去重
		}
	}
	System.out.println("去除重复子串后的全部子串有："+set.size()+"个");
	System.out.println("分别是：" + set);
}
```
运行结果：
```java
字符串aaab 有非空子串a, b, aa, ab, aaa, aab, aaab，一共 7 个
去除重复子串后的全部子串有：7个
分别是：[aa, aaa, a, ab, b, aab, aaab]
```

', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584366026862&di=62fd65c3b51d4c6b8052f954ef561268&imgtype=0&src=http%3A%2F%2Fimg.article.pchome.net%2F00%2F38%2F63%2F92%2Fpic_lib%2Fs960x639%2F08s960x639.jpg', '2020-03-13 11:21:46', '2020-03-14 16:19:35', 1, 2);
INSERT INTO myblog.blog (id, title, content, picture, createTime, updateTime, type, author) VALUES (6, 'mybatis根据日期查询、查询日期并返回', '### 方法：

#### 1.查询日期，返回日期字符串
```handlebars
//查询所有博客对应的年份，返回一个集合
List<String> findGroupYear();
```
#### 2.根据日期查询
```handlebars
//根据年份查询博客信息
List<Blog> findByYear(@Param("year") String year);
```


### mybatis映射:


```java
<select id="findGroupYear" resultType="String">
    select DATE_FORMAT(b.update_time, ''%Y'') from t_blog b
</select>


<select id="findByYear" resultType="Blog">
    select b.title, b.update_time, b.id, b.flag
    from t_blog b
    where DATE_FORMAT(b.update_time, ''%Y'') = #{year}
</select>
```

## 总结：
**DATE_FORMAT(date,format)：date表示日期，format表示显示的格式。**
**该方法可以把date类型转换为String类型的字符串**
', 'http://p.qpic.cn/dnfbbspic/0/dnfbbs_dnfbbs_dnf_gamebbs_qq_com_forum_202002_04_082156ifotspmtuzcffycn.jpg/0', '2020-03-21 11:54:41', '2020-03-21 15:33:02', 1, 2);
INSERT INTO myblog.blog (id, title, content, picture, createTime, updateTime, type, author) VALUES (10, '蓝桥杯：基础练习 01字串（java）', '## 试题 基础练习 01字串

### 资源限制
时间限制：1.0s   内存限制：256.0MB
### 问题描述
对于长度为5位的一个01串，每一位都可能是0或1，一共有32种可能。它们的前几个是：

00000

00001

00010

00011

00100

请按从小到大的顺序输出这32种01串。

### 输入格式
本试题没有输入。
### 输出格式
输出32行，按从小到大的顺序每行一个长度为5的01串。
### 样例输出
00000
00001
00010
00011
<以下部分省略>

#### 思路：
**使用Integer.toBinaryString()将十进制整数转换为二进制字符串，再判断长度是否能整除5，在前面加0输出**

```java
public class 字串01 {
	public static void main(String[] args) {

		for(int i = 0; i <= 31; i++) {
			String s = Integer.toBinaryString(i);
			int len = s.length();
			switch(len % 5) {
			case 1: s = "0000" + s;break;
			case 2: s = "000" + s;break;
			case 3: s = "00" + s;break;
			case 4: s = "0" + s;break;
			case 0: break;
			}
			System.out.println(s);
		}
	}
}
```

**另一种方法，不用判断加0，也可以直接使用printf输出指定格式的整数：**
```java
public class 字串01 {
	public static void main(String[] args) {

		for(int i = 0; i <= 31; i++) {
			String s = Integer.toBinaryString(i);
			int n = Integer.parseInt(s);
			System.out.printf("%05d\\n",n);
		}
	}
}
```

', 'http://5b0988e595225.cdn.sohucs.com/images/20190112/12a825d7c0f94223a0b1fc3dcddc6570.jpeg', '2020-03-21 16:19:41', '2020-03-21 16:19:41', 1, 2);
INSERT INTO myblog.blog (id, title, content, picture, createTime, updateTime, type, author) VALUES (11, '安东尼经典语录', '我怕你说话，我怕你沉默。
我怕你来到，我怕你离开。
我怕抓紧你，我怕放走你。
我怕你看到我，我怕你无视我。
我怕你想念我，我怕你忘记我。
我怕你过于依赖我，我怕你不再需要我。
我怕你爱我，我怕你不爱我。
我怕你太爱我，我怕你不够爱我。

不管昨夜经历了怎样的泣不成声 早晨醒来这个城市依然车水马龙

不要让那个喜欢你的人 撕心裂肺地为你哭那么一次 因为 你能把他伤害到那个样子的机会 只有一次 那一次以后 你就从不可或缺的人变成可有可无的人了 即使他还爱你 可是 总有一些东西真的改变了

应该趁着年轻 和喜欢的人一起 制造些比夏天还要温暖的事

风不懂云的漂泊。天不懂雨的落魄。眼不懂泪的懦弱。所以你不懂我的选择。也可以不懂我的难过。不是每一个人都一定要快乐。不是每一种痛都一定要诉说

后来，我终于能接受，我们不会再在一起这个实事。我想我唯一能做的就是，继承那些，你拥有的让我着迷的品质，好好地生活下去。

如果你喜欢的人不喜欢你，那么就算全世界的人都喜欢你，还是会觉得孤独吧。If the person you like doesn''t like you ,wouldn''t it still be lonely even if the whole world loves you.

那些 我们一直惴惴不安 又充满好奇的未来 会在心里隐隐约约地觉得它们是明亮的

人生，总会有不期而遇的温暖，和生生不息的希望。

其实我一直相信 等你出现的时候我就知道是你

我谈过最长的恋爱，就是自恋，我爱自己，没有情敌。

不知道如何爱你，看着你，是我唯一的方式。I don''t know how to love you ,looking at you is the only way i know

我所知道的关于你的事情 就只有天气预报了

我觉得 我们俩之间就像喝酒 我干杯 你随意

玫瑰当然爱小王子
不过当你真的喜欢一个人的时候 就会想很多 会很容易办蠢事 说傻话
更别说 那个人像小王子那么可爱
玫瑰很温柔 其实她只是不知所措罢了
至于小王子 他还太小了 不明白玫瑰的温柔
他的离开也许并不是坏事

最喜欢早上，好像什么都可以重新开始，中午的时候就开始觉得忧伤，晚上最难度过。

I know that one day I will forgot you.
I don''t have anticipation.
I don''t feel sad.
I just know, that''s all.

长大了 总有那么一两次机会 你会为了喜欢的人 跑那么一跑 因为 如果是对的人的话 走路真的来不及

很久以后，那些好极了和糟透了的时刻我们都会忘记，唯一真实和难忘的是，我们抬头挺胸走过的人生。

不论是我的世界车水马龙繁华盛世 还是它们都瞬间消失化为须臾 我都会坚定地走向你 不慌张 不犹豫', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584886233933&di=984c5adffbeee9133453b53cdfaa912f&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F4ec2d5628535e5dd24d1534b74c6a7efcf1b62a1.jpg', '2020-03-22 19:36:45', '2020-03-22 19:36:45', 5, 2);

INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (2, 1);
INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (3, 4);
INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (3, 1);
INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (4, 5);
INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (6, 7);
INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (6, 1);
INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (5, 1);
INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (10, 1);
INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (11, 8);
INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (1, 2);
INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (1, 4);
INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (1, 7);
INSERT INTO myblog.blog_tag (blogId, tagId) VALUES (1, 8);

INSERT INTO myblog.comment (id, content, createTime, userId, blogId, parentId, toUserId) VALUES (1, '111', '2020-11-16 19:01:13', 3, 2, null, null);
INSERT INTO myblog.comment (id, content, createTime, userId, blogId, parentId, toUserId) VALUES (3, '姬霓太美', '2020-11-08 10:33:35', 1, 11, null, null);
INSERT INTO myblog.comment (id, content, createTime, userId, blogId, parentId, toUserId) VALUES (4, '17张牌你能秒我？', '2020-11-08 10:34:14', 2, null, 3, 1);
INSERT INTO myblog.comment (id, content, createTime, userId, blogId, parentId, toUserId) VALUES (5, '。。。', '2020-11-08 10:36:42', 1, null, 3, 2);
INSERT INTO myblog.comment (id, content, createTime, userId, blogId, parentId, toUserId) VALUES (6, '只因你太美', '2020-11-09 20:12:04', 2, 11, null, null);
INSERT INTO myblog.comment (id, content, createTime, userId, blogId, parentId, toUserId) VALUES (7, '呸', '2020-11-09 20:12:34', 1, null, 6, 2);
INSERT INTO myblog.comment (id, content, createTime, userId, blogId, parentId, toUserId) VALUES (8, '喝，，tui', '2020-11-09 20:13:16', 2, null, 6, 2);
INSERT INTO myblog.comment (id, content, createTime, userId, blogId, parentId, toUserId) VALUES (15, 'ffff', '2020-11-14 12:28:37', 2, null, 3, 2);
INSERT INTO myblog.comment (id, content, createTime, userId, blogId, parentId, toUserId) VALUES (22, '嘎嘎嘎', '2020-11-16 19:04:11', 2, 2, null, null);
INSERT INTO myblog.comment (id, content, createTime, userId, blogId, parentId, toUserId) VALUES (23, '松果弹抖闪电鞭', '2020-11-19 19:05:30', 3, 5, null, null);