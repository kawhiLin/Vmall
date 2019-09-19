#!/bin/bash
# 获取工作目录

# 镜像构建版本
image_version=$1;
echo $image_version;
if [ -z "$1" ]; then
    echo "build version is null!"
    exit
fi
echo "构建版本：$1";
mkdir -p images;

docker login -u cff794f73c7d428e8d68704e1eed88bf@GIF65ELSH9POGCBWWPG0 -p 8e94b891089a2ae986151271b820b8057ab76baa56accf4dad82faa1d4ec7110 192.168.69.130:20202
for dir in $(ls -l | grep ^d| awk '{print $9}'|grep -v images); do
        echo "---------------开始构建镜像：${dir}:${image_version}---------------";
        docker build -t ${dir}:${image_version} ${dir}
        docker tag  ${dir}:${image_version} 192.168.69.130:20202/vmall/${dir}:${image_version}
        docker push 192.168.69.130:20202/vmall/${dir}:${image_version}
        echo "Saving...Executing in the background"
        nohup docker save -o ./images/${dir}-${image_version}.tar ${dir}:${image_version} &
done
