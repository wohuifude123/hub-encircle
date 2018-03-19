package com.project.start;

import com.project.start.listener.DashBoardListener;
import com.project.service.VerticleMain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GraduationMain {

    public static void main(String[] args) {
        GraduationMain main = new GraduationMain();
        main.start();
    }

    private void start() {

        try {

            // 采集任务

            // 开启接口方式
            VerticleMain.deployVertx();

            // 监听浏览器发送的消息
            DashBoardListener dashBoardListener = new DashBoardListener();
            Thread dashBoardListenerThread = new Thread(dashBoardListener);
            dashBoardListenerThread.start();

        } catch (Exception e) {
            log.error("start error",e);
        }
    }



}