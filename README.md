项目搭建过程中参考的资料：
https://spring.io/guides/
https://spring.io/guides/gs/serving-web-content/
https://elasticsearch.cn/explore（）
以及一些创建ssh秘钥和下载git所参考的网站，此处不列出

项目搭建过程中遇到的问题
进度1：
创建第一个spring boot 项目
在controller出现警告，并且项目无法运行，于是在HelloController中添加了@ResponseBody的注解。
进度2
启动第一个spring boot项目（但是提交的数据没有被表单正确接收，不晓得是地址错误还是数据错误）（未解决）
进度3
正在将项目上传至github：
问题1，使用 git add . 命令出现警告，原因：我是Windows系统，但是项目是mac写的，换行符的转义出现了问题。
解决方法，添加了一句话：git config --global core.autocrlf false
问题2，使用git push -u origin main出现错误，原因：少了一个known_hosts文件，本来密钥文件应该是三个，现在是两个
解决方法，直接yes，自动生成。
问题3，接问题2，似乎没有生成对应的公钥，昨天的ssh秘钥设置出现问题？是否重新设置捏。
解决方法，秘钥的设置没有勾选读写选项，设置成只读了，将秘钥删除后重新设置，勾选了写入选项。
