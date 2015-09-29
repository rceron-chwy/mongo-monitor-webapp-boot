package com.mongodash.dump;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class AbstractMongoUtility {

	public Long propagateException(Future<Long> future) throws IOException {
		
		long result = 0;
		
		try {
			result = future.get();
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			future.cancel(true);
		} catch (ExecutionException ee) {
			throw new IOException(ee.getCause());
		}
		
		return result;
		
	}

}