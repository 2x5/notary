package controllers;

import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

import com.google.code.morphia.logging.MorphiaLoggerFactory;
import com.google.code.morphia.logging.slf4j.SLF4JLoggerImplFactory;

//@OnApplicationStart
public class Bootstrap extends Job {
    
    public void doJob() {
    	Logger.debug("^*^*^*^ BOOTSTRAP executing ^*^*^*^ ");
    }
}