package me.hydos.J64;

import me.hydos.J64.util.EmuManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

	public static final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

	public static void main(String[] args) {
		EmuManager.setup();
		while(true) {
			try {
				queue.take().run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
