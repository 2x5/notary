package controllers;

import java.util.Date;
import java.util.List;

import models.Notary;
import play.Logger;
import play.modules.morphia.Model;
import play.modules.morphia.Model.MorphiaQuery;
import play.mvc.Controller;

import com.mongodb.CommandResult;
import com.mongodb.DBCollection;

public class Application extends Controller {

    public static void index() {
    	flash.clear();
        render();
    }
    
    public static void search(String last, String zip, int limit) {
    	Logger.debug("searching last[%s], zip[%s]", last, zip);
    	
    	flash.clear(); // not sure why needed but..
    	
    	if (allEmpty(last, zip)) {
    		flash.error("Enter some search criteria.");
    		render();
    	}
    	if (zip.length() != 0 && !zip.matches("\\d{5}")) {
    		flash.error("Zip Code must be a 5-digit number.");
    		render();
    	}

    	long start = System.currentTimeMillis();

    	if (limit == 0) limit = 100;
    	
    	MorphiaQuery q = Notary.q().limit(limit);
    	
    	if (!allEmpty(last)) {
    		q.filter("last_s", last.toLowerCase());
    	}
    	if (!allEmpty(zip)) {
    		q.filter("zip", Integer.parseInt(zip));
    	}
    	Logger.debug(q.getQueryObject().toString());
    	
    	List<Notary> results = q.asList();
    	
    	long stop =  System.currentTimeMillis();
    	long countAll = q.count();
    	
    	long stopCount =  System.currentTimeMillis();
    	
    	
    	if (results.size() == 0) {
    		flash.error("No records found.");
    	} else if (countAll > limit) {
    		flash.success("Results limited to %s records.", limit);
    	}
    	
    	String message1 = "Retrieved " + results.size() + " rows in " + 
    			(stop - start) + " ms. ";
    	String message2 = "Counted " + countAll + " rows in " + 
    			(stopCount - stop) + " ms." ;
    	

    	DBCollection notary = Model.db().getCollection("notary");
    	CommandResult stats = notary.getDB().getStats();
    	String message0 = " Stats: " + stats;
    	
    	render(results, message0, message1, message2);
    }
    
    public static void stats() {
    	Logger.debug("** stats requested by %s / %s", request.remoteAddress, request.host);
    	flash.success("%s", new Date());
    	render();
    }
    
    private static boolean allEmpty(String... strings) {
    	for (String s : strings) {
    		if (s != null && ! s.isEmpty()) {
    			return false;
    		}
    	}
    	return true;
    }

    // notes
	// List<Notary> results = Notary.q().filter("last", last).filter("zip", zip).asList();
	// List<Notary> results = Notary.find("byLast", last).limit(limit).asList();
	// Play! JPA-style:
	// List<User> users = User.find("byCountryAndDepartment", "China", "IT").asList();
    // List<Notary> results = Notary.find("byLastAndZip", last, zip).asList();
	
	//	if (!morphiaLogInitialized) {
	//	    MorphiaLoggerFactory.registerLogger(SLF4JLoggerImplFactory.class);	
	//  }
    
}