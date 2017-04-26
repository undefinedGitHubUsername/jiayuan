package com.undefined2023.crawler.tryspring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

@SuppressWarnings("deprecation")
public class DrawingApp {
	public static void main(String[] args) {

		BeanFactory bf = new XmlBeanFactory(
				new FileSystemResource("spring.xml"));
		Circle circle = (Circle) bf.getBean("circle");

		circle.draw();
	}
}
