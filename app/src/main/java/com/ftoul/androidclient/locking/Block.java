package com.ftoul.androidclient.locking;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.ftoul.androidclient.R;

/**
 * Created by 公页 on 2016/9/29.
 */
public class Block {
    float mCenterPointX;//圆心x
    float mCenterPointY;//圆心y
    float mBigRadius;//大圆半径
    float mLittleRadius;//小圆半径
    BlockSate mState = BlockSate.IDLE;//默认空闲

    int mId;//索引
    //空闲状态颜色
    int mIdleBigCircleColor = Color.parseColor("#FFFFFF");
    int mIdleLittleCircleColor = Color.parseColor("#f74c4c");

    //选中状态颜色
    int mHittedBigCircleColor = Color.parseColor("#f74c4c");
    int mHittedLittleCircleColor = Color.parseColor("#f74c4c");

    //密码通过的颜色
    int mSuccessBigCircleColor = Color.parseColor("#f74c4c");
    int mSuccessdLittleCircleColor = Color.parseColor("#f74c4c");

    //密码错误时的颜色
    int mErroBigCircleColor = Color.parseColor("#60000000");
    //三角
    Path mArrow = new Path();

    int mErroLittleCircleColor = Color.parseColor("#ff0000");
    //三角指向角度,水平向右为0度，顺时针方向为正
    double mArrowAngle;

    public void setArrowAngle(double angle){
        mArrowAngle = angle;
    }

    public void drawArrow(Canvas canvas, Paint paint){
        //没有松手，则不画三角
        if(mState != BlockSate.SUCCESS && mState != BlockSate.ERRO){
            return;
        }

        float arrowLen = (mBigRadius - mLittleRadius)*0.5f;
        float arrowLeftX = mCenterPointX + mLittleRadius + (mBigRadius - mLittleRadius - arrowLen)/2;
        float arrowRightX = arrowLeftX + arrowLen;
        float topY = mCenterPointY - arrowLen;
        float bottomY = mCenterPointY + arrowLen;
        mArrow.moveTo(arrowRightX, mCenterPointY);
        mArrow.lineTo(arrowLeftX, topY);
        mArrow.lineTo(arrowLeftX, bottomY);
        mArrow.close();

        canvas.save();
        canvas.rotate((float) mArrowAngle, mCenterPointX, mCenterPointY);
        canvas.drawPath(mArrow, paint);
        canvas.restore();
    }
    public enum BlockSate {
        IDLE,//空闲
        HITTED,//手指触摸
        ERRO,//密码错误
        SUCCESS;//密码正确
    }

}
