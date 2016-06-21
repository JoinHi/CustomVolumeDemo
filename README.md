# CustomVolumeDemo
一个显示音量增减的自定义view
参照鸿洋大神的自定义view进行了一些修改，可以让周围的音量平均显示

    <com.zzj.customvoice.CustomRadio
        android:id="@+id/customRadio"
        android:layout_centerInParent="true"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_gravity="bottom"
        android:layout_marginLeft="10dp"
        android:layout_marginBottom="20dp"
        zhy:bg="@mipmap/voice"//中间的图片
        zhy:circleWidth="10dp"//每格音量宽度
        zhy:spaceCount="4"//下方空白的大小
        zhy:dotCount="16"//音量共有16个等级
        zhy:firstColor="#c9c8c8"
        zhy:secondColor="#000102"
        zhy:splitSize="25" />//每格音量的间隔
