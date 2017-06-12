package com.speedata.activity.print;

import com.sprt.sprtlabelprinter.BasePrinter;
import com.sprt.sprtlabelprinter.BasePrinter.PAlign;
import com.sprt.sprtlabelprinter.BasePrinter.PBarcodeType;
import com.sprt.sprtlabelprinter.BasePrinter.PRotate;

@SuppressWarnings("unused")
public class MySPRTLabel1 {
    // mm -> print
    private static final int MULTIPLE = 8;

    // 字体大小
    public static final int fontSizeLong = 48;
    public static final int fontSizeBig = 36;
    public static final int fontSizeMid = 28;
    public static final int fontSizeSmall = 20;
    // 线条宽度
    private static final int line_width_border = 1;
    private static final int line_width_separator = 1;

    // private static final int print_margin = 10;
    private static final int margin_horizontal = 0;
    private static final int margin_vertical = 3 * MULTIPLE;
    // 表格边框占用大小
    private static final int page_height = 55 * MULTIPLE;
    private static final int page_width = 90 * MULTIPLE;

    // 每行的高度
    private static final int[] row_height = {4 * MULTIPLE, 4 * MULTIPLE,
            4 * MULTIPLE, 4 * MULTIPLE, 4 * MULTIPLE, 13 * MULTIPLE};
    // 每行的宽度
    private static final int[] line_width = {19 * MULTIPLE, 9 * MULTIPLE,
            25 * MULTIPLE, 9 * MULTIPLE, 20 * MULTIPLE};
    private static final int border_width = page_width - 2 * margin_horizontal;
    private static final int border_height = page_height - 2 * margin_vertical;
    private static final int top_left_x = margin_horizontal;
    private static int top_left_y = margin_vertical;// 32
    private static final int top_right_x = border_width;
    // private static final int top_right_x = top_left_x + border_width;
    private static final int top_right_y = top_left_y;
    private static final int bottom_left_x = top_left_x;
    private static final int bottom_left_y = top_left_y + border_height;
    private static final int bottom_right_x = top_right_x;
    private static final int bottom_right_y = bottom_left_y;

    // String centerName,
    public static void doPrint(BasePrinter iPrinter, String location,
                               String id, String unit, String name, String price, String spec,
                               String orgin, String max, String lower, String barcode)//
    {
        // 设置页面大小
        iPrinter.pageSetup(page_width, page_height);
        // drawBox(iPrinter);
        drawHorizontalSeparator(iPrinter);// 画横线
        drawVerticalSeparator(iPrinter);// 画所有竖线
        drawContent(iPrinter, location, id, unit, name, price, spec, orgin,
                max, lower, barcode);// 画表体内容
        // drawRow2Content(iPrinter, codeStr);
        // drawLableText(iPrinter);
        // // centerName,
        // drawContentText(iPrinter, destinationStr, customerName, userSite,
        // receiverAddress, arrSite1, arrSite2, arrSite3, arrSite4,
        // arrSite5);
        // draw37Column3Content(iPrinter, countStr, serialStr, packModeStr,
        // dispatchModeStr, weightAndTiji);
        // drawBarcodeContent(iPrinter, subCodeStr);
        // iPrinter.drawGraphic(20, 20, 0, 0, bmp);
        iPrinter.print(1, 1);
    }


    private static int x_start = 10;
    private static int x_end = 300;
    private static int y_start = 20;
    private static int y_end = 300;

    /**
     * @param iPrinter
     * @param projectName 项目部
     * @param user        用料单位 施工队
     * @param name        材料名称
     * @param spec        规格型号
     * @param count
     * @param barcode
     * @param time        领料日期
     */
    public static void doPrintOutRegister(BasePrinter iPrinter, String projectName, String user,
                                          String name, String
                                                  spec, String count, String barcode, String
                                                  time, int printCount) {

        for (int i = 0; i < printCount; i++) {
            iPrinter.pageSetup(75 * 8, 63 * 8);
            int width = top_left_x + line_width_border * 5 + line_width[0]
                    + line_width[1] + line_width[2] + line_width[3] + line_width[4]
                    + 5 * 8;
            int height = top_left_y + line_width_border * 6 + row_height[0]
                    + row_height[1] + row_height[2] + row_height[3] + row_height[4]
                    + row_height[5] - 2;
            String head_projectName = "项 目 部: " + projectName;
            String head_user = "用料单位: " + user;
            String head_name = "材料名称: " + name;
            String head_count = "数    量: " + count;
            String head_spec = "规格型号: " + spec;
            String head_time = "领料日期: " + time;
            String head_barcode = "条形码 ";
            int row_11_start_x = 2 * 8 + line_width_border;
            int row_11_end_x = 600;//row_11_start_x + line_width[0];
            int row_11_start_y = 7 * 8 + line_width_border;
            int row_11_end_y = row_11_start_y + row_height[0];
            iPrinter.drawText(row_11_start_x, row_11_start_y, row_11_end_x,
                    row_11_end_y, PAlign.START, PAlign.CENTER, 0, 0,
                    head_projectName, fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 项目部
            int row_21_start_y = row_11_end_y + line_width_separator;
            int row_21_end_y = row_21_start_y + row_height[1];
            iPrinter.drawText(row_11_start_x, row_21_start_y, row_11_end_x,
                    row_21_end_y, PAlign.START, PAlign.CENTER, 0, 0, head_user,
                    fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 用料单位

            int row_31_start_y = row_21_end_y + line_width_separator;
            int row_31_end_y = row_31_start_y + row_height[2];
            iPrinter.drawText(row_11_start_x, row_31_start_y, row_11_end_x,
                    row_31_end_y, PAlign.START, PAlign.CENTER, 0, 0, head_name,
                    fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 材料名称
            // 规格型号
            int row_41_start_y = row_31_end_y + line_width_separator;
            int row_41_end_y = row_41_start_y + row_height[3];
            iPrinter.drawText(row_11_start_x, row_41_start_y, row_11_end_x,
                    row_41_end_y, PAlign.START, PAlign.CENTER, 0, 0, head_spec,
                    fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 规格型号
//
            int row_51_start_y = row_41_end_y + line_width_separator;
            int row_51_end_y = row_51_start_y + row_height[4];
            iPrinter.drawText(row_11_start_x, row_51_start_y, row_11_end_x,
                    row_51_end_y, PAlign.START, PAlign.CENTER, 0, 0, head_count,
                    fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 数量
            int row_61_start_y = row_51_end_y + line_width_separator - 1 * 8;
            int row_61_end_y = row_61_start_y + row_height[5];
            int bar_margin = 15;
            // 打印条形码
            iPrinter.drawBarCode(row_11_start_x+30, row_61_start_y +10,
                    row_11_end_x + 150, row_61_end_y - 4 * 8, PAlign.START, PAlign.START, 0,
                    1 * bar_margin, barcode, PBarcodeType.CODE128, 2, 15 * MULTIPLE,
                    PRotate.Rotate_0);

//            int row_71_start_y = row_61_end_y + line_width_separator;
//            int row_71_end_y = row_71_start_y + line_width_separator;
            // 打印条形码数字
//            iPrinter.drawText(row_11_start_x + 5 * 10, row_71_start_y, row_11_end_x,
//                    row_71_end_y, PAlign.START, PAlign.CENTER, 0, 0,
//                    barcode, fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);
            iPrinter.drawText(row_11_start_x + 12 * 10, row_61_end_y + 30, row_11_end_x + 40,
                    row_61_end_y + 4 * 8 + 30, PAlign.START, PAlign.CENTER, 0, 0,
                    barcode, fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);

            int row_81_start_y = row_61_end_y + line_width_separator;
            int row_81_end_y = row_81_start_y + row_height[5];
            // 第几张 送料人 收料人
            int row_82_start_x = row_11_start_x + line_width[0]
                    + line_width_separator;// 最高
            int row_82_end_x = row_82_start_x + line_width[0];
            int row_83_start_x = row_82_end_x + line_width[1];// 最高内容
            int row_83_end_x = row_83_start_x + line_width[2];
            iPrinter.drawText(row_11_start_x, row_81_start_y + 30, row_82_start_x,
                    row_81_end_y + 30, PAlign.START, PAlign.CENTER, 0, 0,
                    (i + 1) + "/" + printCount, fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);
            iPrinter.drawText(row_82_start_x - 80, row_81_start_y + 30, row_82_end_x - 80,
                    row_81_end_y + 30, PAlign.START, PAlign.CENTER, 0, 0,
                    "领料人：", fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);
            iPrinter.drawText(row_83_start_x - 80, row_81_start_y + 30, row_83_end_x,
                    row_81_end_y + 30, PAlign.START, PAlign.CENTER, 0, 0,
                    "发料人：", fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);

            int row_91_start_y = row_81_end_y + line_width_separator;
            int row_91_end_y = row_91_start_y + row_height[1];
            iPrinter.drawText(row_11_start_x, row_91_start_y, row_11_end_x,
                    row_91_end_y, PAlign.START, PAlign.CENTER, 0, 0,
                    head_time, fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);

            //int horizontal,int skip
            iPrinter.print(0, 0);
        }
    }


    public static void doPrintInRegister(BasePrinter iPrinter, String projectName, String supper,
                                         String goodsName, String
                                                 spec, String count, String barcode, int
                                                 printCount) {
//        doPrint(iPrinter,"xx","xx","xxx","xxxx","xxxxx","xxxxxx","xxxxxxx","xxxxxxx","xfd","sd");
        for (int i = 0; i < printCount; i++) {
            iPrinter.pageSetup(75 * 8, 63 * 8);
            int width = top_left_x + line_width_border * 5 + line_width[0]
                    + line_width[1] + line_width[2] + line_width[3] + line_width[4]
                    + 5 * 8;
            int height = top_left_y + line_width_border * 6 + row_height[0]
                    + row_height[1] + row_height[2] + row_height[3] + row_height[4]
                    + row_height[5] - 2;
            String head_projectName = "项 目 部: " + projectName;
            String head_user = "供 应 商: " + supper;
            String head_name = "材料名称: " + goodsName;
            String head_count = "数    量: " + count;
            String head_spec = "规格型号: " + spec;
//            String head_time = "领料日期:" + time;
            String head_barcode = "条形码 ";
            int row_11_start_x = 2 * 8 + line_width_border;
            int row_11_end_x = 600;//row_11_start_x + line_width[0];
            int row_11_start_y = 7 * 8 + line_width_border;
            int row_11_end_y = row_11_start_y + row_height[0];
            iPrinter.drawText(row_11_start_x, row_11_start_y, row_11_end_x,
                    row_11_end_y, PAlign.START, PAlign.CENTER, 0, 0,
                    head_projectName, fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 项目部
            int row_21_start_y = row_11_end_y + line_width_separator;
            int row_21_end_y = row_21_start_y + row_height[1];
            iPrinter.drawText(row_11_start_x, row_21_start_y, row_11_end_x,
                    row_21_end_y, PAlign.START, PAlign.CENTER, 0, 0, head_user,
                    fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 用料单位

            int row_31_start_y = row_21_end_y + line_width_separator;
            int row_31_end_y = row_31_start_y + row_height[2];
            iPrinter.drawText(row_11_start_x, row_31_start_y, row_11_end_x,
                    row_31_end_y, PAlign.START, PAlign.CENTER, 0, 0, head_name,
                    fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 材料名称
            // 规格型号
            int row_41_start_y = row_31_end_y + line_width_separator;
            int row_41_end_y = row_41_start_y + row_height[3];
            iPrinter.drawText(row_11_start_x, row_41_start_y, row_11_end_x,
                    row_41_end_y, PAlign.START, PAlign.CENTER, 0, 0, head_spec,
                    fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 规格型号
//
            int row_51_start_y = row_41_end_y + line_width_separator;
            int row_51_end_y = row_51_start_y + row_height[4];
            iPrinter.drawText(row_11_start_x, row_51_start_y, row_11_end_x,
                    row_51_end_y, PAlign.START, PAlign.CENTER, 0, 0, head_count,
                    fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 数量
            int row_61_start_y = row_51_end_y + line_width_separator - 1 * 8;
            int row_61_end_y = row_61_start_y + row_height[5];
            int bar_margin = 15;
            // 打印条形码
            iPrinter.drawBarCode(row_11_start_x+30, row_61_start_y+10,
                    row_11_end_x, row_61_end_y - 4 * 8, PAlign.START, PAlign.START, 0,
                    0, barcode, PBarcodeType.CODE128, 5, 15 * MULTIPLE,
                    PRotate.Rotate_0);

//            iPrinter.drawBarCode(0,0,barcode,PBarcodeType.CODE128, 1000, 15 * MULTIPLE,
//                    PRotate.Rotate_0);

//            int row_71_start_y = row_61_end_y + line_width_separator;
//            int row_71_end_y = row_71_start_y + line_width_separator;
            // 打印条形码数字
//            iPrinter.drawText(row_11_start_x + 5 * 10, row_71_start_y, row_11_end_x,
//                    row_71_end_y, PAlign.START, PAlign.CENTER, 0, 0,
//                    barcode, fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);
            iPrinter.drawText(row_11_start_x + 12 * 10, row_61_end_y + 30, row_11_end_x,
                    row_61_end_y + 4 * 8 + 30, PAlign.START, PAlign.CENTER, 0, 0,
                    barcode, fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);

//            int row_81_start_y = row_61_end_y + 4 * 8 + line_width_separator;
//            int row_81_end_y = row_81_start_y + line_width_separator;
            int row_81_start_y = row_61_end_y + line_width_separator;
            int row_81_end_y = row_81_start_y + row_height[5];
            // 第几张 送料人 收料人
            int row_82_start_x = row_11_start_x + line_width[0]
                    + line_width_separator;// 最高
            int row_82_end_x = row_82_start_x + line_width[0];
            int row_83_start_x = row_82_end_x + line_width[1];// 最高内容
            int row_83_end_x = row_83_start_x + line_width[2];
            iPrinter.drawText(row_11_start_x, row_81_start_y + 25, row_82_start_x,
                    row_81_end_y + 25, PAlign.START, PAlign.CENTER, 0, 0,
                    (i + 1) + "/" + printCount, fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);
            iPrinter.drawText(row_82_start_x - 80, row_81_start_y + 25, row_82_end_x - 80,
                    row_81_end_y + 25, PAlign.START, PAlign.CENTER, 0, 0,
                    "送料人：", fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);
            iPrinter.drawText(row_83_start_x - 80, row_81_start_y + 25, row_83_end_x,
                    row_81_end_y + 25, PAlign.START, PAlign.CENTER, 0, 0,
                    "收料人：", fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);

            //int horizontal,int skip
//            iPrinter.print(0, 0);
        }
    }


    private static void drawBox(BasePrinter iPrinter) {
        // 设置框体大小
        int border_top_left_y = top_left_y;// 152
        // iPrinter.drawBox(line_width_border, top_left_x, border_top_left_y,
        // bottom_right_x, bottom_right_y);
        // iPrinter.drawBox(1, 24, border_top_left_y,
        // bottom_right_x, bottom_right_y);
        iPrinter.drawBox(1, 24, border_top_left_y, bottom_right_x,
                41 * MULTIPLE);
    }

    /**
     * 画所有横线
     *
     * @param iPrinter
     */
    private static void drawHorizontalSeparator(BasePrinter iPrinter) {
        int width = top_left_x + line_width_border * 5 + line_width[0]
                + line_width[1] + line_width[2] + line_width[3] + line_width[4]
                + 5 * 8;
        int temp = top_left_y; // 边框，也即第一条横线的纵坐标
        iPrinter.drawLine(line_width_border, top_left_x, temp, width, temp);
        for (int i = 0; i < row_height.length; i++) {
            int start_x = top_left_x;
            int end_x = top_right_x;
            temp += row_height[i];
            iPrinter.drawLine(line_width_border, start_x, temp, width, temp);
        }
    }

    /**
     * @param iPrinter
     */
    private static void drawVerticalSeparator(BasePrinter iPrinter) {
        int height = top_left_y + line_width_border * 6 + row_height[0]
                + row_height[1] + row_height[2] + row_height[3] + row_height[4]
                + row_height[5] - 2;
        int width = top_left_x + line_width_border * 5 + line_width[0]
                + line_width[1] + line_width[2] + line_width[3] + line_width[4]
                + 5 * 8;

        // 画第一条竖线
        iPrinter.drawLine(line_width_separator, top_left_x, top_left_y,
                top_left_x, height - 4);
        // 画第二条竖线
        iPrinter.drawLine(line_width_separator, top_left_x + line_width[0]
                + line_width_separator, top_left_y, top_left_x + line_width[0]
                + line_width_separator, height - 4);
        // // 画第六条竖线
        iPrinter.drawLine(line_width_separator, width, top_right_y, width,
                height - 4);

        // 第三条竖线
        int width_53 = line_width_separator * 2 + top_left_x + line_width[0]
                + line_width[1];
        int hight_5 = top_left_y + line_width_separator * 4 + row_height[0]
                + row_height[1] + row_height[2] + row_height[3];
        int hight_6 = hight_5 + row_height[4];
        iPrinter.drawLine(line_width_separator, width_53, hight_5, width_53,
                hight_6 - 4);
        // 第四条竖线
        int hight_2 = top_left_y + line_width_separator + row_height[0];
        int width_42 = line_width_separator * 3 + top_left_x + line_width[0]
                + line_width[1] + line_width[2];
        iPrinter.drawLine(line_width_separator, width_42, hight_2, width_42,
                hight_6 - 4);
        // 第五条竖线
        int width_52 = width_42 + line_width[3];
        iPrinter.drawLine(line_width_separator, width_52, hight_2, width_52,
                hight_6 - 4);
    }

    private static void drawContent(BasePrinter iPrinter, String location,
                                    String id, String unit, String name, String price, String spec,
                                    String orgin, String max, String lower, String barcode) {
        int width = top_left_x + line_width_border * 5 + line_width[0]
                + line_width[1] + line_width[2] + line_width[3] + line_width[4]
                + 5 * 8;
        int height = top_left_y + line_width_border * 6 + row_height[0]
                + row_height[1] + row_height[2] + row_height[3] + row_height[4]
                + row_height[5] - 2;
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
        // 仓库位置
        int row_11_start_x = 2 * 8 + line_width_border;
        int row_11_end_x = row_11_start_x + line_width[0];
        int row_11_start_y = 7 * 8 + line_width_border;
        int row_11_end_y = row_11_start_y + row_height[0];
        iPrinter.drawText(row_11_start_x, row_11_start_y, row_11_end_x,
                row_11_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0,
                head_location, fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 仓库位置
        int row_12_start_x = row_11_end_x + line_width_border;
        iPrinter.drawText(row_12_start_x, row_11_start_y, top_right_x,
                row_11_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, location,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 仓库位置内容
        // 物资编号
        int row_21_start_x = row_11_start_x;
        int row_21_end_x = row_11_end_x;
        int row_21_start_y = row_11_end_y + line_width_separator;
        int row_21_end_y = row_21_start_y + row_height[1];
        iPrinter.drawText(row_21_start_x, row_21_start_y, row_21_end_x,
                row_21_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, head_id,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 物资编号
        int row_22_start_x = 2 * 8 + line_width_border * 2 + line_width[0];// 物资编号内容
        int row_22_end_x = 2 * 8 + line_width_border * 2 + line_width[0]
                + line_width[1] + line_width[2];
        int row_23_start_x = 2 * 8 + line_width_border * 3 + line_width[0]
                + line_width[1] + line_width[2];// 单位
        int row_23_end_x = row_23_start_x + line_width[3];
        int row_24_start_x = 2 * 8 + line_width_border * 4 + line_width[0]
                + line_width[1] + line_width[2] + line_width[3];// 单位内容
        int row_24_end_x = row_24_start_x + line_width[4];

        iPrinter.drawText(row_22_start_x, row_21_start_y, row_22_end_x,
                row_21_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, id,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 编号内容
        iPrinter.drawText(row_23_start_x, row_21_start_y, row_23_end_x,
                row_21_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, head_unit,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 单位
        iPrinter.drawText(row_24_start_x, row_21_start_y, row_24_end_x,
                row_21_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, unit,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 单位内容
        // 物资名称
        int row_31_start_y = row_21_end_y + line_width_separator;
        int row_31_end_y = row_31_start_y + row_height[2];
        iPrinter.drawText(row_21_start_x, row_31_start_y, row_21_end_x,
                row_31_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, head_name,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 物资名称

        iPrinter.drawText(row_22_start_x, row_31_start_y, row_22_end_x,
                row_31_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, name,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 物资名称内容
        iPrinter.drawText(row_23_start_x, row_31_start_y, row_23_end_x,
                row_31_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, head_price,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 单价
        iPrinter.drawText(row_24_start_x, row_31_start_y, row_24_end_x,
                row_31_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, price,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 单价内容

        // 规格型号
        int row_41_start_y = row_31_end_y + line_width_separator;
        int row_41_end_y = row_41_start_y + row_height[3];
        iPrinter.drawText(row_21_start_x, row_41_start_y, row_21_end_x,
                row_41_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, head_spec,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 规格型号

        iPrinter.drawText(row_22_start_x, row_41_start_y, row_22_end_x,
                row_41_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, spec,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 规格型号内容
        iPrinter.drawText(row_23_start_x, row_41_start_y, row_23_end_x,
                row_41_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, head_orgin,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 产地
        iPrinter.drawText(row_24_start_x, row_41_start_y, row_24_end_x,
                row_41_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, orgin,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 产地内容

        // 储备定额
        int row_51_start_y = row_41_end_y + line_width_separator;
        int row_51_end_y = row_51_start_y + row_height[4];
        iPrinter.drawText(row_21_start_x, row_51_start_y, row_21_end_x,
                row_51_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, head_ration,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 储备定额
        int row_52_start_x = row_21_start_x + line_width[0]
                + line_width_separator;// 最高
        int row_52_end_x = row_52_start_x + line_width[1];
        int row_53_start_x = row_52_end_x + line_width_separator;// 最高内容
        int row_53_end_x = row_53_start_x + line_width[2];
        iPrinter.drawText(row_52_start_x, row_51_start_y, row_52_end_x,
                row_51_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, head_max,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 最高
        iPrinter.drawText(row_53_start_x, row_51_start_y, row_53_end_x,
                row_51_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, max,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 最高内容

        iPrinter.drawText(row_23_start_x, row_51_start_y, row_23_end_x,
                row_51_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, head_lower,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 最低
        iPrinter.drawText(row_24_start_x, row_51_start_y, row_24_end_x,
                row_51_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, lower,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 最低内容

        // 条形码
        int row_61_start_x = 2 * 8 + line_width_border;
        int row_61_end_x = row_61_start_x + line_width[0];
        int row_61_start_y = row_51_end_y + line_width_separator - 1 * 8;
        int row_61_end_y = row_61_start_y + row_height[5];
        iPrinter.drawText(row_61_start_x, row_61_start_y, row_61_end_x,
                row_61_end_y, PAlign.CENTER, PAlign.CENTER, 0, 0, head_barcode,
                fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);// 条形码
        int row_62_start_x = row_61_end_x + line_width_border;
        int bar_margin = 6;
        // 打印条形码
        iPrinter.drawBarCode(row_62_start_x + 3 * 8, row_61_start_y - 3 * 8 + 4,
                width, row_61_end_y - 4 * 8, PAlign.CENTER, PAlign.START, 0,
                1 * bar_margin, barcode, PBarcodeType.CODE128, 1, 9 * MULTIPLE,
                PRotate.Rotate_0);
        // a(210,310) d(709,382)
//		iPrinter.drawBarCode(210, 280, 709, 352, PAlign.CENTER, PAlign.CENTER,
//				0, 0, "123456789012", PBarcodeType.CODE128, 1, 9 * MULTIPLE,
//				PRotate.Rotate_0);


        int x_start = row_62_start_x + 5 * 8;
        int x_end = width - row_62_start_x;
        // 打印条形码数字
        iPrinter.drawText(row_62_start_x + 3 * 8, row_61_end_y - 3 * 8, width,
                row_61_end_y + 1 * 8, PAlign.CENTER, PAlign.START, 0, 0,
                barcode, fontSizeSmall, 1, 0, 0, 0, PRotate.Rotate_0);
        // 我的210*********709**********539
    }

}
