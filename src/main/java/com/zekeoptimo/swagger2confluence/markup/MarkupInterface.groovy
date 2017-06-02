package com.zekeoptimo.swagger2confluence.markup


/**
 * Created by zekeo on 4/30/2017.
 */
interface MarkupInterface {
    String preProcessing(String text)
    String postProcessing (String text)
    String line(String text)
}
