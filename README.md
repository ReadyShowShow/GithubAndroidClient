# SimpleGitHub
简易版的github客户端
This is a simple android github client

介绍(introduction)
------------------
只是一个Android客户端，用于搜索、star/本地收藏 github上的开源项目

it's just a android client, used to search open source from github

本项目将尽量严格按照MVP模式开发
其中会使用一些比较新的流行技术，当做学习

目前使用到的(used Library)
-------------------------
- 响应式编程：rxJava
- ~~数据库：realm，尽管有些坑，但还是在继续使用~~(线程问题实在麻烦，还是换回SQLite)
- 图片加载：glide
- 网络：okhttp、retrofit

目前已实现的功能(feature)
------------------------
- 搜索比较流行的语言的开源项目：java、OC、swift、C、C++等
- 按关键字搜索
- 收藏和取消收藏
- 收藏的project按关键字搜索
- 登录查看自己的star

接下来会做的(todo list)
--------------------
- [ ] 单种语言增加查看最近多少天的trends
- [ ] 对star的project进行分类展示、收藏等（目前github上不能对star分类，不太方便）