package com.undefined2023.crawler.jiayuan;

import java.io.File;

import com.undefined2023.base.FileUtil;

public class Statistic {
	private int total = 0;
	private int nonexistent = 0;
	private int blacklisted = 0;
	private int closed = 0;

	private int gal = 0;
	private int dating = 0;
	private int image = 0;
	private int age = 0;
	private int height = 0;

	private int beijing = 0;
	private int hunan = 0;

	public static int vip = 0;
	public static int diamond = 0;

	public void start(int begin, int end, File f)
			throws InterruptedException {
		if (begin <= end)
			return;
		for (int i = begin; i >= end; i--) {
			String id = String.valueOf(i);
			User u = new User(id);
			String line = Conf.HOMEPAGE_URL + id;
			try {
				if (u.available()) {
					if (u.female()) {
						this.gal += 1;
						line += ", gal";
						if (u.available()) {
							this.dating += 1;
							line += ", dating";
							if (u.hasPhoto()) {
								this.image += 1;
								line += ", image";
								line += ", serious";
								String age = u.age();
								line += ", " + age;
								if (u.ageUnder(24)) {
									this.age += 1;
									String height = u.height();
									line += ", " + height;
									if (u.tallerThanMom()) {
										this.height += 1;
										line += ", height";
										if (u.inBeijing()) {
											this.beijing += 1;
											line += ", beijing";
										} else if (u.inHunan()) {
											this.hunan += 1;
											line += ", hunan";
										}
									}
								}
								FileUtil.writeLine(line, f);
								total++;
							}
						}
					}
				}
			} catch (IndexOutOfBoundsException ioobe) {
				if (u.blacklisted()) {
					blacklisted += 1;
					line += " blacklist";
				} else if (u.closed()) {
					closed += 1;
					line += " close";
				} else if (u.nonexistent()) {
					nonexistent += 1;
					line += " nonexistence";
				}
			} catch (Exception e) {
				Thread.sleep(Conf.SLEEP);
				String statistic = "total: " + total + ", nonexistence: "
						+ nonexistent + ", blacklist: " + blacklisted
						+ ", close: " + closed + ", gal: " + gal + ", dating: "
						+ dating + ", image: " + image + ", age: " + age
						+ ", height: " + height + ", beijing: " + beijing
						+ ", hunan: " + hunan;
				FileUtil.writeLine(statistic, f);
				if (i >= end)
					return;
				start(i, end, f);
			}
			total += 1;
		}
	}
}
