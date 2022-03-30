创建第一个spring boot 项目
在controller出现警告，并且项目无法运行，于是在HelloController中添加了@ResponseBody的注解。（添加ResponseBody是错误的！！！）
启动第一个spring boot项目（但是提交的数据没有被表单正确接收，不晓得是地址错误还是数据错误）（已解决，参考下文）
正在将项目上传至github：

问题1，使用 git add . 命令出现警告，原因：我是Windows系统，在换行符的转义出现了问题。
解决方法，添加了一句话：git config --global core.autocrlf false

问题2，使用git push -u origin main出现错误，原因：少了一个known_hosts文件，本来密钥文件应该是三个，现在是两个
解决方法，直接yes，自动生成。

问题3，接问题2，似乎没有生成对应的公钥，昨天的ssh秘钥设置出现问题？是否重新设置捏。
解决方法，秘钥的设置没有勾选读写选项，设置成只读了，将秘钥删除后重新设置，勾选了写入选项。

补充说明：git add. 之后需要 get commit -m “说明”来提交，之后需要用get push来提交到远端
如果有小修改的话，则使用 git commit --amend --no-edit

进度4用bootstrap来快速搭建导航条
在修改完成网页后，启动项目，出现了刚开始的问题，这次知道了问题的原因，springboot项目只返回了值，没能正确的找到网页。
解决方法：一开始添加ResponseBody的注解就是错误的，将其删除后return到了正确的网页，而不是返回字符串

进度5申请github登录权限
比较顺利，没有出现太大的问题

进度6实现读取session和cookie
电脑太卡了？出现time out！？
java.net.SocketTimeoutException: Read timed out

重新设置了超时时间- -。
OkHttpClient client = new OkHttpClient.Builder()
.connectTimeout(10, TimeUnit.SECONDS)
.readTimeout(20,TimeUnit.SECONDS)
.build();

进度7安装并配置好MYSQL数据库
以前有装过，重新装得卸载干净，而且计算机命名不能为中文
控制的权限组也要重新添加，好麻烦QAQ

之后用Navicat工具手动建表和随手添加了数据

create table `user`
(
`id`           int auto_increment primary key,
`account_id`   varchar(100),
`name`         varchar(50),
`token`        char(36),
`gmt_create`   bigint,
`gmt_modified` bigint
);

进度8通过spring boot集成mybatis
好烦哦- -，插入报错，
Error while adding the mapper 'interface shuodog.community.mapper.UserMapper' to configuration
好吧，大括号写成小括号了，难绷

插入数据库又出错了- -。
呜呜呜，少个小括号。太难了- -。

插入数据库数据一直为空，不知道为什么，太难了

从GitHub中返回的消息方法体为
"message": "Must specify access token via Authorization header. https://developer.github.com/changes/2020-02-10-deprecating-auth-through-query-param",
"documentation_url": "https://docs.github.com/v3/#oauth2-token-sent-in-a-header"
意思是请求方法已经过时了，需要更安全的请求手段
呜呜呜，过时了你更新API文档啊，我写了这么久，一直查不到bug是什么，搞得我调试了半天

//        Request request = new Request.Builder()
//                .url("https://api.github.com/user?access_token="+accessToken)
//                .build();


        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();


CREATE TABLE `community`.`Untitled`  (
`id` int NOT NULL AUTO_INCREMENT,
`title` varchar(50) NULL,
`description` text NULL,
`creator` int NULL,
`comment_count` int ZEROFILL NULL,
`read_count` int ZEROFILL NULL,
`like_count` int ZEROFILL NULL,
`unlike_count` int ZEROFILL NULL,
`tag` varchar(255) NULL,
PRIMARY KEY (`id`)
);


进度9通过bootstrap来快速搭建发布问题界面

唔，之前写的流式界面还有点问题，虽然分左右两栏，并且在缩写页面时让右栏下滑
但是右栏会和左边的发布按钮以及警告框重叠
暂时是修改了右栏的padding-top值

报错报错还是报错，数据库语句不是少括号，就是名字打错，而且报警告也不报错
返回的字符串也写错了，太难了，查了两天两夜的BUG

唔，虽然用随机生成的UUID作为一个唯一标识符token储存在数据库和网页cookie中
但是要手动清除网页cookie或者从数据库里删除，有点麻烦，而且网页cookie为空会报错
需要手动判断一下

额，不同于登录按钮和用户名的显示，在发布问题的页面中
需要使用不同的th模板来传值，在页面中用th:value来传值，在后台也同样用value获取值，
并且需要设置required = false，因为需要显示错误信息到网页上，而不是在后台报错
唔，但是文本框可以正常传值，输入框还不行

进度10新增头像url获取的属性
考虑获取GitHub中的用户头像并显示出来
需要在user中重新添加属性，重新设置git方法，好麻烦- -。
又学会使用新的插件lombok了，用这个插件可以不用手动设置get、set方法
接下来把头像合理的显示在界面上即可

进度11继续用bootstrap来写首页显示

不知道为什么引入了css样式没生效！！！

很烦- -。
好吧，浏览器的问题，IE可以正常显示。但是谷歌浏览器不行

其他的文本都能正常取出，但是头像的url取不出来
原因：
复制对象的时候，从数据库取出的值的名字与类变量设置的名字不太一致
数据库使用下划线，类变量中使用驼峰法命名
在配置中将驼峰法命名转换打开
mybatis.configuration.map-underscore-to-camel-case=true


进度12写分页逻辑
不是前后端分离好难写啊
逻辑好混乱- -，而且感觉好复杂

先好好理一下逻辑QAQ
首先确定一下首页的显示数据数量、一页五条左右吧，
然后通过select count（1）查询一下question表的全部数据量，
将总数/5取向上取整可以得到总的页码数

之后如何显示数据呢- -。
先把所有的question查询到一个list，然后每五个显示吗。
感觉不合逻辑，而且数据量多了，一次查询几百条也是显示五条，
没有什么必要。


呃呃呃，原来数据库查询可以一段一段查询哇，
通过limit 0 5可以查询第一页
limit 1 5可以查询第二页
这样就好做了，在点击对应页码时再开查询
从前端获取页码和显示条数，之后查询后显示就行

接下来思考一下页码的导航条如何显示
首先有四个按钮，第一页，上一页，下一页，最后一页，而且显示的页码数也是一个问题
先暂定显示七页吧，那么页码数就作为一个数组传入前端
我希望当前页在中间—— 第一页 前一页 123  4（当前页） 567 后一页 最后一页
大概是这样显示。
额，如果当前页是第一页怎么显示呢，也是显示七页吗
如果也显示七页，那么页码数组就是for（i<7）然后把当前页后面的六个页码也加进数组
这样每次点击当前页始终是第一个格子显示1（当前页）234567      2（当前页）345678

那我固定返回七个页面，再判断当前页是不是在中间。不在中间，每次点击下一页，返回的数组不变
1（当前页）234567   12（当前页）34567

不对不对，这样的话，最后一页又要跟第一页一样额外判断
最后一页肯定是不可能显示在中间的，也不可能显示在第一格，
我也没办法用for（i<7）把数组确定出来
就像345678   9（当前页也是最后一页）
我得从当前页为6开始判断，判断后面是不是还有页码了
没有了，那么页码数组不更新了

而且很尴尬的一点是，如果页码数不够七页怎么办，
我在for（i<7）中还得判断一下

而且如果只有八页，那也要做很多次判断
一开始就用for（i<7）
1、2、3页的时候判断三次，当前页不在中间 页码数组固定为1234567
当前页为4，跳到第五页的时候，返回的页码数组为2345678，那这个数组又怎么构造
也就是说，我还得开一个变量，时刻记录着页码数组的第一个值，
呜呜呜，太麻烦了，逻辑又乱，判断又多，写起来肯定都是BUG



那如果我放弃把页码塞在中间呢，每七页显示一个数组
唔，好像也不是不行，每次页码数组被遍历完一次，就返回下一个页码数组

或者说也不一定要固定显示七个数据，只要把页固定数组在中间的话，当前页++往后塞三个
当前页--往前塞三个值，没有就不显示 比如1（当前页）234 下一页 最后一页
第一页 前一页 678 9（当前页）

唔这两种简单多了，而且页码数组的返回判断也简单，从一般显示的习惯来看，第二种应该是比较常用的
第二种的当前页很容易显示在中间

额，暂定第二种了，
首先是页码数组生成问题（用for（i<=3））
里面写两个判断
if(当前页前面有页码)
｛
add（0，当前页-i）
｝
if(当前页后面有页码)
｛
add（当前页+i）
｝

之后用bool判断一下什么时候显示第一页，前一页，后一页，最后一页这几个按钮就行


通过在前端界面中使用th模板——th：class$来动态设置class，使得出现选中的效果
在<a>标签中使用th：href@来获取返回的网址


进度13编辑个人界面
唔。这个就比较简单了，首页问题显示和页码写好的情况下
把代码直接搬过来用就行，随便改一两处地方就好
个人页面的话，先在左栏显示自己的问题信息和其他人的回复信息，返回的数据表单和页码与首页的是通用的
所以可以复用很大一片代码
然后在右边就写一个菜单栏，通过点击菜单栏来展示不同的内容，这个的话，跟页码点击原理差不多
通过都是点击以后再开查询，再用后台的查询返回前端界面



呃呃呃，收回之前说的话，在这里得重新配置地址，因为不是在根目录下的网页跳转
而是在同一个网页中显示不同的内容，而且URL中需要显示回传的数据- -。得问一下怎么用th模板来拼接URL
而且后台回传的网址肯定也不一样了，噢，烦耶
可以用类似th:href="@{'/profile/'+${section}(currentPage=${currentPage})}"的格式来拼接URL


而且还有一个问题。虽然只是多写了一个界面，但是导航条要用很多次，
此处的话，可以使用<div th:insert="~{navigation :: nav}"></div>来插入所有界面中共同的内容


而且这次写后端的代码有很多地方都是复制的，但是又要各种改参数，是不是应该重新封装一个方法啊
哦，淦，重新封装的话，接口怎么写，虽然大部分逻辑是一样的，可是参数不一样- -。
但是分开写，万一之后有什么功能添加，就是到处改，我想一想接下来写的界面，是不是有什么相同的地方
把验证和查询重新封装算了。


进度14用拦截器把登录验证重写了一遍
因为登录验证的逻辑本身就写好了，所以只要重写拦截器的函数就行
重写后把之前的验证代码拷贝进去，然后就可以将多余的验证代码删去
比较尴尬的是，拦截器太强了，什么都能拦。把我的静态资源文件导入也拦住了
所以百度了一下，在webConfig里加了一个函数体，重新声明静态资源文件不用拦截

进度15编写问题界面
通过首页点击问题的标题跳转至问题界面
为了布局风格的统一，也分成左右两栏，左边放问题和描述，底下放评论
右边可以放问题的发布者，底下可以放一些同类型标签的推荐

跟之前一样，通过ID来查询question，然后在前端界面拼接一下url

进度16完善登录退出功能
在用拦截器验证登录后就很方便了，
再做一下退出功能，
登录验证是验证用随机生成的UUID为值的token
只要在cookie中清除已经存好的token就可以了
但是之前还发现了另一个问题，就是每次登录，
就会无限重新创建新的用户，
导致输入的问题与用户不能对应
这个问题在写question界面的时候我还以为是bug
好吧，确实算是bug
登录的时候，不光要验证cookie，还需要判断一下用户的accountID是否已经存在
如果存在的话就更新用户信息而不是重新创建