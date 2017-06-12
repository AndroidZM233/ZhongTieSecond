package com.android.print.demo.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.print.sdk.Barcode;
import com.android.print.sdk.PrinterConstants;
import com.android.print.sdk.PrinterConstants.Command;
import com.android.print.sdk.PrinterInstance;
import com.speedata.dreamdemo.R;

public class LabelUtils {
    // mm -> print
    private static final int MULTIPLE = 8;

    // �����С
    public static final int fontSizeLong = 48;
    public static final int fontSizeBig = 36;
    public static final int fontSizeMid = 28;
    public static final int fontSizeSmall = 20;
    // �������
    private static final int line_width_border = 2;
    private static final int line_width_separator = 1;

    // private static final int print_margin = 10;
    private static final int margin_horizontal = 3 * MULTIPLE;
    private static final int margin_vertical = 4 * MULTIPLE;
    // ���߿�ռ�ô�С
    private static final int page_width = 75 * MULTIPLE;
    private static final int page_height = 90 * MULTIPLE;

    // ÿ�еĸ߶�
    private static final int[] row_height = {15 * MULTIPLE, 10 * MULTIPLE,
            10 * MULTIPLE, 10 * MULTIPLE, 8 * MULTIPLE, 10 * MULTIPLE,
            7 * MULTIPLE, 12 * MULTIPLE};
    private static final int[] ver_height = {95, 95, 95, 95};
    //15 10 10 10 8 8 21
    // private static final int row_height = 100;
    // ������, -2 * margin
    private static final int border_width = page_width - 2 * margin_horizontal;
    private static final int border_height = page_height - 2 * margin_vertical;
    private static final int top_left_x = margin_horizontal;
    private static final int top_left_y = margin_vertical;//32
    private static final int top_right_x = top_left_x + border_width;
    private static final int top_right_y = top_left_y;
    private static final int bottom_left_x = top_left_x;
    private static final int bottom_left_y = top_left_y + border_height;
    private static final int bottom_right_x = top_right_x;
    private static final int bottom_right_y = bottom_left_y;
    // 3��7��
    private static final int row36_column1_width = 10 * MULTIPLE;
    private static final int row37_column3_width = 20 * MULTIPLE;
    private static final int row36_sep1_x = top_left_x + row36_column1_width;//104
    private static final int row37_sep2_x = top_right_x - row37_column3_width;//392
    // ��һ��
    private static final int row1_icon_width = 34 * MULTIPLE;
    private static final int row1_sep1_x = top_left_x + row1_icon_width;

    //    private static void drawBarcodeContent(PrinterInstance iPrinter,
//                                           String subCodeStr)
//    {
//
//        int row3_start_y = top_left_y + row_height[0] + row_height[1];//264
//        int row4_start_y = row3_start_y + row_height[2];//344
//        int row5_start_y = row4_start_y + row_height[3];//424
//        //Y轴变化  是 2 3 4 5
//        int row6_start_y = row5_start_y + row_height[4];//488
//        int row7_start_y = row6_start_y + row_height[5];//552
//        int row8_start_y = row7_start_y + row_height[6];
//        int barcode_height = 15 * MULTIPLE;
//        int barcode_height1 = 13 * MULTIPLE;
//        int bar_margin = 8;
//        int area_start_x = top_left_x;
////		int area_start_y = top_left_y + row_height[0] + row_height[1]
////				+ row_height[2] + row_height[3] + row_height[4] + row_height[5];
//        int area_start_y =row7_start_y;//532
//        int area_start_y1 =row6_start_y;//424
//        int area_end_x = row37_sep2_x;
//        int area_end_y = bottom_right_y-1*bar_margin;//
//        int start=536+8;
//        int end=start+barcode_height1;
//        int area_end_y1 = area_start_y+barcode_height1;
//        int barcode_y = area_start_y + bar_margin;//528
//        int barcode_y1 = area_start_y1 + bar_margin;
//        iPrinter.prn_DrawBarcode(area_start_x, area_start_y + 18, area_end_x,
//                area_end_y1, PAlign.CENTER, PAlign.START, 0, barcode_y,
//                "1234667999559500000000", PBarcodeType.CODE128, 1, barcode_height1,
//                PRotate.Rotate_0);
////		int bartext_y = barcode_y + barcode_height + bar_margin;//656
////		int bartext_y=area_start_y+barcode_height1+8;//584
//        int bartext_y=area_start_y+8 + barcode_height1+1*bar_margin;//584
//        int bartext_y1 = barcode_y + barcode_height - 2*bar_margin;
//
//        iPrinter.prn_DrawText(area_start_x, bartext_y, area_end_x, area_end_y+8,
//                PAlign.CENTER, PAlign.CENTER,0,0,"1234667999559500000000" , fontSizeSmall,
//                0, 0, 0, 0, PRotate.Rotate_0);
//        Log.e("test1", "bartext_y:" + bartext_y + "   area_end_y:" + area_end_y + "  高度：" +
//                (area_end_y - bartext_y));
//    }
    public static String label_print(int horizontal, int skip) {
        String str = null;
        if (horizontal == 1) {
            horizontal = 2;
        } else {
            horizontal = 0;
        }
        if (skip == 1) {
            str = "PR " + horizontal + "\r\nFORM\r\nPRINT\r\n";
        } else {
            str = "PR " + horizontal + "\r\nPRINT\r\n";
        }
        Log.i("zmg", str);
        return str;
    }



    public void printAssignTable(PrinterInstance mPrinter, String location,
                                 String id, String unit, String name, String price, String spec,
                                 String orgin, String max, String lower, String barcode) {
        System.out
                .println("执行了这段代码"
                        + "*************************************************************");
        // 55*95
        String head_location = "仓库位置";
        String head_id = "物资编号";
        String head_unit = "单位";
        String head_name = "物资名称";
        String head_price = "单价";
        String head_spec = "规格型号";
        String head_orgin = "产地";
        String head_ration = "储备定额";
        String head_max = "最高";
        String head_lower = "最低";
        String head_barcode = "条形码";
        int line_y1 = 15, line_y2 = 80, line_y3 = 145, line_y4 = 205, line_y5 = 270, line_y6 = 335, line_y7 = 435;
        int line_x1 = 30, line_x2 = 145, line_x3 = 205, line_x4 = 515, line_x5 = 575, line_x6 = 735;
        int line_width = 1;

//		mPrinter.printText(R.string.str_label + "");
//		mPrinter.setPrinter(Command.CLOCKWISE_ROTATE_90, 1);
        mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, Command.PRINT_AND_NEWLINE);
        mPrinter.prn_PageSetup(777, 450);// 设置纸张大小

        // 添加线条, 参数与PDA上一致
        mPrinter.prn_DrawLine(line_width, line_x1, line_y1, line_x1, line_y7);// 第一条竖线
        mPrinter.prn_DrawLine(line_width, line_x2, line_y1, line_x2, line_y7);// 第二条竖线
        mPrinter.prn_DrawLine(line_width, line_x3, line_y5, line_x3, line_y6);// 第三条竖线
        mPrinter.prn_DrawLine(line_width, line_x4, line_y2, line_x4, line_y6);// 第四条竖线
        mPrinter.prn_DrawLine(line_width, line_x5, line_y2, line_x5, line_y6);// 第五条竖线
        mPrinter.prn_DrawLine(line_width, line_x6, line_y1, line_x6, line_y7);// 第六条竖线

        mPrinter.prn_DrawLine(line_width, line_x1, line_y1, line_x6, line_y1);// 第一条横线
        mPrinter.prn_DrawLine(line_width, line_x1, line_y2, line_x6, line_y2);// 第二条横线
        mPrinter.prn_DrawLine(line_width, line_x1, line_y3, line_x6, line_y3);// 第三条横线
        mPrinter.prn_DrawLine(line_width, line_x1, line_y4, line_x6, line_y4);// 第四条横线
        mPrinter.prn_DrawLine(line_width, line_x1, line_y5, line_x6, line_y5);// 第五条横线
        mPrinter.prn_DrawLine(line_width, line_x1, line_y6, line_x6, line_y6);// 第六条横线
        mPrinter.prn_DrawLine(line_width, line_x1, line_y7, line_x6, line_y7);// 第七条横线

        // 添加表头
        mPrinter.prn_DrawText(line_x1 + 5, line_y1 + 20, head_location, "宋体",
                24, 0, 0, 0, 0);// 仓库位置
        mPrinter.prn_DrawText(line_x1 + 5, line_y2 + 20, head_id, "宋体", 24, 0,
                0, 0, 0);// 物资编号
        mPrinter.prn_DrawText(line_x4 + 5, line_y2 + 20, head_unit, "宋体", 24,
                0, 0, 0, 0);// 单位
        mPrinter.prn_DrawText(line_x4 + 5, line_y3 + 20, head_price, "宋体", 24,
                0, 0, 0, 0);// 单价
        mPrinter.prn_DrawText(line_x4 + 5, line_y4 + 20, head_orgin, "宋体", 24,
                0, 0, 0, 0);// 产地
        mPrinter.prn_DrawText(line_x4 + 5, line_y5 + 20, head_lower, "宋体", 24,
                0, 0, 0, 0);// 最低
        mPrinter.prn_DrawText(line_x2 + 5, line_y5 + 20, head_max, "宋体", 24, 0,
                0, 0, 0);// 最高
        mPrinter.prn_DrawText(line_x1 + 5, line_y3 + 20, head_name, "宋体", 24,
                0, 0, 0, 0);
        mPrinter.prn_DrawText(line_x1 + 5, line_y4 + 20, head_spec, "宋体", 24,
                0, 0, 0, 0);
        mPrinter.prn_DrawText(line_x1 + 5, line_y5 + 20, head_ration, "宋体", 24,
                0, 0, 0, 0);
        mPrinter.prn_DrawText(line_x1 + 5, line_y6 + 40, head_barcode, "宋体",
                24, 0, 0, 0, 0);

        // 表体内容
        mPrinter.prn_DrawText(line_x2 + 5, line_y1 + 20, location, "宋体", 24, 0,
                0, 0, 0);// 仓库位置
        mPrinter.prn_DrawText(line_x2 + 5, line_y2 + 20, id, "宋体", 24, 0, 0, 0,
                0);// 物资编号
        mPrinter.prn_DrawText(line_x5 + 5, line_y2 + 20, unit, "宋体", 24, 0, 0, 0,
                0);// 单位
        mPrinter.prn_DrawText(line_x2 + 5, line_y3 + 20, name, "宋体", 24, 0, 0, 0,
                0);// 物资名称
        mPrinter.prn_DrawText(line_x5 + 5, line_y3 + 20, price, "宋体", 24, 0, 0, 0,
                0);// 单价
        mPrinter.prn_DrawText(line_x2 + 5, line_y4 + 20, spec, "宋体", 24, 0, 0, 0,
                0);// 规格型号
        mPrinter.prn_DrawText(line_x5 + 5, line_y4 + 20, orgin, "宋体", 24, 0, 0, 0,
                0);// 产地
        mPrinter.prn_DrawText(line_x3 + 5, line_y5 + 20, max, "宋体", 24, 0, 0, 0,
                0);// 最高
        mPrinter.prn_DrawText(line_x5 + 5, line_y5 + 20, lower, "宋体", 24, 0, 0, 0,
                0);// 最低
        // 添加条码
        @SuppressWarnings("unused")
        int barcodetype = 12; // 这里不要改---表示code-128编码格式
        mPrinter.prn_DrawBarcode(line_x2 + 15, line_y6 + 10, barcode, 12, 0, 0,
                80);
        // 执行打印
        mPrinter.prn_PagePrint(1);
//		mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);
    }





    public void printLabel(PrinterInstance mPrinter, String barcode) {

        mPrinter.prn_PageSetup(360, 120);
        Barcode barcodemn =new Barcode(PrinterConstants.BarcodeType.CODE128, 2, 150, 2, barcode);
//        Barcode barcodemn =new Barcode(PrinterConstants.BarcodeType.CODE128, 2, 150, 2, "0Z5000000001");
        mPrinter.printBarCode(barcodemn);
//        mPrinter.prn_DrawBarcode(110, 35, barcode, 12, 0, 40, 0);
        mPrinter.prn_DrawText(150, 100, barcode, "宋体", 23, 0, 0, 0, 0);

        label_print(0, 1);
        // 执行打印
        mPrinter.setPrinter(0, 1);
        mPrinter.prn_PagePrint(0);

//        mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 3);
//        mPrinter.sendByteData(new byte[]{0x0C});
//          mPrinter.printText(R.string.str_label+"");
//          mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 3);

        //数据模拟
//	    String userCode="044298";
//	    String departmentCityName1="上海市";
//	    String send="";
//	    String destTransCenterName1="廊坊枢纽中";
//	    String destinationName1="北京海淀区四季青营业部";


//	    String pieces="0002/2";
//	    String goodsType="";
//	    String wrapType1="2纸";
//	    String wblCode1="944789305";
//	    String transType1="精准汽运";


//		// 边框
//		int line_x1=5;
//		int line_x2=636;
//		int line_y1=1;
//		int line_y2=101-5;
//		int line_y3=199-5-5;
//		int line_y4=252-5-5;
//		int line_y5=305-5-5;
//		int lineWidth=2;
//		int split_x=113;

        // 添加线条, 参数与PDA上一致
//		mPrinter.prn_DrawLine(lineWidth,line_x1,line_y1,line_x2,line_y1);
//		mPrinter.prn_DrawLine(lineWidth,line_x1,line_y2,line_x2,line_y2);
//		mPrinter.prn_DrawLine(lineWidth,line_x1,line_y3,line_x1+split_x*4,line_y3);
//		mPrinter.prn_DrawLine(lineWidth,line_x1,line_y4,line_x2,line_y4);
//		mPrinter.prn_DrawLine(lineWidth,line_x1,line_y5,line_x2,line_y5);
//
//		mPrinter.prn_DrawLine(lineWidth,line_x1,line_y1,line_x1,line_y5);//侧边两列下拉
//		mPrinter.prn_DrawLine(lineWidth,line_x2,line_y1,line_x2,line_y5);//侧边两列下拉
//
//		mPrinter.prn_DrawLine(lineWidth,line_x1+split_x  ,line_y3+lineWidth,line_x1+split_x  ,
// line_y5);
//		mPrinter.prn_DrawLine(lineWidth,line_x1+split_x*2,line_y3+lineWidth,line_x1+split_x*2,
// line_y5);
//		mPrinter.prn_DrawLine(lineWidth,line_x1+split_x*3,line_y3+lineWidth,line_x1+split_x*3,
// line_y5);
//		mPrinter.prn_DrawLine(lineWidth,line_x1+split_x*4,line_y1          ,line_x1+split_x*4,
// line_y5);


//		mPrinter.prn_DrawText(15,305,"德邦物流","宋体",32,0,0,1,0);
//		mPrinter.prn_PageSetup(360,150);
//		mPrinter.prn_DrawText(50, 10, "材料名称：XX物资", "宋体", 28, 0, 0, 0, 0);
//		mPrinter.prn_DrawText(50, 90, "规格型号：XX物资", "宋体", 28, 0, 0, 0, 0);
        //添加条码
//		int barcodetype=12; // 这里不要改---表示code-128编码格式
//		mPrinter.prn_DrawBarcode(50, 10, barcode, 12, 0, 0, 60);
        //PBarcodeType.CODE128
//		mPrinter.drawBarCode( 50, 140,
//				"1234667999559500000000", PBarcodeType.CODE128, 1, 60);
        // 条码下方的数字
//		mPrinter.prn_DrawText(100, 80, barcode, "宋体", 23, 0, 0, 0, 0);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //添加标签打印时间
//		mPrinter.prn_DrawText(30, 280, sdf.format(new Date()), "宋体", 23, 0, 0, 0, 0);
        // 执行打印
//		mPrinter.prn_PagePrint(0);
//		mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 3);
        //添加出发外场所属城市
//		int iLenght = departmentCityName1.length();
//		if (iLenght<3) {
//
//			mPrinter.prn_DrawText(8,130,departmentCityName1,"",30,0,0,0,0);
//
//		}else if(iLenght==3){
//			String str1 = departmentCityName1.substring(0,2);
//			String str2 = departmentCityName1.substring(2);
//			mPrinter.prn_DrawText(8, 118, str1, "宋体", 30, 0, 0, 0, 0);
//    		mPrinter.prn_DrawText(20, 147, str2, "宋体", 30, 0, 0, 0, 0);
//
//		}else{ // 大于三个, 但最终只打印四个
//			String str1 = departmentCityName1.substring(0,2);
//			String str2 = departmentCityName1.substring(2,4);
//			mPrinter.prn_DrawText(8,118,str1,"",30,0,0,0,0);
//			mPrinter.prn_DrawText(8,147,str2,"",30,0,0,0,0);
//		}
//		// 添加送字 自提时不打印 收传入的数据决定
//		mPrinter.prn_DrawText(3,20,send,"",30,0,0,0,0);
//
//		//到达外场----长度在前面已经判断好了
//		mPrinter.prn_DrawText(181, 15, destTransCenterName1, "宋体", 45, 0, 0, 0, 0);
//
//		//目的站 大于六个字的时候字体小一号
//		destinationName1 = "-"+ destinationName1;
//		if (destinationName1.length()<=7) {
//			mPrinter.prn_DrawText(14*8-35,125,destinationName1,"",45,0,0,0,0);
//		}else{
//			mPrinter.prn_DrawText(77, 125, destinationName1, "宋体", 35, 0, 0, 0, 0);
//		}
//
//			//中转外场编码  最多四个
//			mPrinter.prn_DrawText(18+110*0, 192, "D02", "黑体", 45, 0, 0, 0, 0);
//    		mPrinter.prn_DrawText(18+110*0, 245, "27", "黑体", 45, 0, 0, 0, 0);
//
//    		mPrinter.prn_DrawText(18+110*1, 192, "D03", "黑体", 45, 0, 0, 0, 0);
//    		mPrinter.prn_DrawText(18+110*1, 245, "510", "黑体", 45, 0, 0, 0, 0);
//
//
//		//件数---这里是一个字体串  如  0001/5  一次性生成
//		mPrinter.prn_DrawText(472, 10, pieces, "宋体", 35, 0, 0, 0, 0);
//
//
//		//包装   只取8小字符
//
//		if (wrapType1.length()>7) {
//			wrapType1 = wrapType1.substring(0, 7);
//		}
//		mPrinter.prn_DrawText(472, 55, wrapType1, "宋体", 30, 0, 0, 0, 0);
//
//		//运单号,运单号分两部分，第二行显示四位,或者五位，第一行根据运单号个数自动扩展
//
//		String b1 = wblCode1.substring(0,5);
//		String b2 = wblCode1.substring(5);
//		mPrinter.prn_DrawText(488, 120, b1, "黑体", 50, 0, 0, 0, 0);
//		mPrinter.prn_DrawText(488, 190, b2, "黑体", 50, 0, 0, 0, 0);
//
//		//运输性质---大于四个字的打大一点
//		if (transType1.length()>4) {
//			mPrinter.prn_DrawText(58*8-2,242,transType1,"",30,0,0,0,1);
//
//		}else{
//			mPrinter.prn_DrawText(462, 252, transType1, "宋体", 25, 0, 0, 0, 1);
//		}

//		// 执行打印
//		mPrinter.prn_PagePrint(0);
//
//		mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 3);


    }


}
