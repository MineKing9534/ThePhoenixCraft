package de.mineking.thephoenixcraft;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Config {
	public String token;

	public long picvid;

	public static Config read(String path) {
		try(Reader reader = new FileReader(path)) {
			return new Gson().fromJson(reader, Config.class);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
