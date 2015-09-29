package com.mongodash.dump;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoDumpCollection implements Callable<Long> {
	
	private static final Logger logger = LoggerFactory.getLogger(MongoDumpCollection.class);
	
    private final DBCollection collection;
    private DumpWriter dumpWriter;
    private DBObject query;
    private String name;
    private final List<Integer> options = new ArrayList<Integer>();

    public MongoDumpCollection(final DBCollection collection, final DumpWriter dumpWriter)
    {
        this.collection = collection;
        this.dumpWriter = dumpWriter;
    }

    public Long call() throws Exception
    {
        final DBCursor cursor = query != null ? collection.find(query) : 
        	collection.find();
        
        cursor.sort(new BasicDBObject("_id", 1));

        for (final Integer option : options)
        {
            cursor.addOption(option);
        }

        long totalRecords = 0;
        while (cursor.hasNext())
        {
            final BasicDBObject dbObject = (BasicDBObject) cursor.next();
            dumpWriter.writeObject(name != null ? name : collection.getName(), dbObject);
            totalRecords++;
        }
        
        cursor.close();
        
        logger.debug("Backup - collection [{}], total records [{}]", collection, totalRecords );
        
        return totalRecords;
    }

    public void setDumpWriter(final DumpWriter dumpWriter)
    {
        this.dumpWriter = dumpWriter;
    }

    public void setQuery(final DBObject query)
    {
        this.query = query;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public void addOption(final Integer option)
    {
        this.options.add(option);
    }

}