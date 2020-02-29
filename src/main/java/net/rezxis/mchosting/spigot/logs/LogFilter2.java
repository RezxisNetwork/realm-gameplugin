package net.rezxis.mchosting.spigot.logs;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class LogFilter2 implements Filter {

	@Override
	public boolean isLoggable(LogRecord record) {
		for (String s : LogFilter.hide) {
			if (record.getMessage().contains(s))
				return false;
		}
		System.out.println("passed : "+record.getMessage());
		return true;
	}

}
