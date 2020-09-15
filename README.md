# qrcode
二维码识别程序
=================

1.1 图片二维码识别如何提高精确度
-----------------
图片模糊、二维码位置不在中间、二维码占比太小等问题导致识别图片中二维码精度通过率偏低，可以通过图片切割，缩圈等方式来解决，具体方法参考本文档1.3

1.2 package qrcode Codes类提供了指定目录下所有二维码图片中二维码识别并打印功能，使用方式参考main方法，输出结果如下例：
-----------------
```
总共生产二维码3个
17.jpg:	https://reservation.starbucks.com.cn/yb/promo?couponId=10020870011XXXX
23.jpg:	https://reservation.starbucks.com.cn/yb/promo?couponId=10020570011XXXX
25.jpg:	https://reservation.starbucks.com.cn/yb/promo?couponId=10020320011XXXX
```

1.3 package util ImageUtil类提供了图片的裁剪功能,解决由于二维码位置不在中间、占比太小，清晰度较低等问题
-----------------
```
支持三种裁剪方式
第一种：是按照横竖几条线平均切割成 row*col份小图 函数名：splitImage
第二种：按照上下方向和左右方向分别指定的百分比裁剪，输出结果为裁剪后的中间部分
第三种：按照上下左右四个方位裁剪，参数也是百分比，输出结果为裁剪后的中间部分
```
1.4 版权声明
-----------------
部分方法来源网络
