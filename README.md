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