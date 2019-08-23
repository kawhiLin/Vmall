## 镜像构建

要求：

- FusionStage版本要求：6.5

- 镜像构建功能正常。



进入镜像构建文件夹，分别在各个子文件夹中，将war包和Dockerfile一起打包为zip包，并上传到软件仓库。

随后在镜像构建页面，创建构建任务、进行构建。



## 部署上线

要求

- 已上传镜像和模板

使用模板创建堆栈。运行成功后，进入web-vmall，通过服务地址+“/Shopping”来访问应用。（注意S大写）

默认管理员账户为admin/admin，预置用户账户为user01/user01



## 接入流水线

- 要求

- FusionStage版本要求：2.2.1

- 已安装CPE，对接gitlab、jenkins。
- 将“jar包依赖-拷贝到jenkins的maven仓库.zip”拷贝到jenkins所运行的节点，解压并将内容复制到“/home/paas/repository”目录下。（web-vmall构建时需要的依赖）

分别在gitlab和jenkins创建vmall项目，完成源码上传。详细步骤参考流水线使用说明。



