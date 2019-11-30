package gui;

public enum Tools {
    line("直线"), polygon("多边形"), ellipse("椭圆"), curve("曲线"), clip("裁剪"), rotate("旋转"), scale("缩放"), translate("平移");

    private String name;

    private Tools(String name){
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
