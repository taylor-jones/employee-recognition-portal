package com.ttt.erp.service;

import org.springframework.stereotype.Service;
import com.ttt.erp.model.Log;
import com.ttt.erp.repository.LogRepository;
import com.ttt.erp.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.Field;
import java.util.ArrayList;

/*
* TR: This is meant to be extended by other service classes that should get logged.
*/

@Service
public class LogService {

  @Autowired
  private LogRepository logRepository;

  @Autowired
  private UserAccountRepository userRepository;

  class PropertyChange {
    private String property;
    private String start;
    private String end;

    public PropertyChange(String property, String start, String end) {
      this.property = property;
      this.start = start;
      this.end = end;
    }

    public String getProperty() { return this.property; }
    public String getStart() { return this.start; }
    public String getEnd() { return this.end; }
  }

  /* This returns a list of differing property names between 2 objects. Uses Java reflections
  * which are pretty cool. Check out some stuff on Java reflections here: 
  * https://stackoverflow.com/questions/10638826/java-reflection-impact-of-setaccessibletrue
  */
  public ArrayList<PropertyChange> entityDiff(Object start, Object end) {
    Field[] startFields = start.getClass().getDeclaredFields();
    Field[] endFields = end.getClass().getDeclaredFields();
    ArrayList<PropertyChange> diffs = new ArrayList<PropertyChange>();
    try {
      for (int i = 0; i < startFields.length; i++) {
        startFields[i].setAccessible(true);
        endFields[i].setAccessible(true);

        String strStart = startFields[i].get(start) != null ? startFields[i].get(start).toString() : "";
        String strEnd = startFields[i].get(end) != null ? startFields[i].get(end).toString() : "";

        if (!strStart.equals(strEnd)) {
          diffs.add (
            new PropertyChange(startFields[i].getName(), strStart, strEnd)
          );
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return diffs;
  }

  public Log logInsert (
    Long userAccountId,
    String controllerClass,
    Long subjectId
  ) {
    return logRepository.save (
      new Log(this.userRepository.findById(userAccountId), controllerClass, subjectId, "insert", null, null, null)
    );
  }

  public ArrayList<Log> logUpdate (
    Long userAccountId, 
    String controllerClass, 
    Long subjectId, 
    Object start, 
    Object end
  ) {
    ArrayList<PropertyChange> properties = this.entityDiff(start, end);
    ArrayList<Log> updates = new ArrayList<Log>();
    properties.forEach ( p -> {
      Log update = logRepository.save (
        new Log (
          this.userRepository.findById(userAccountId), 
          controllerClass, 
          subjectId, 
          "update", 
          p.getProperty(), 
          p.getStart(), 
          p.getEnd()
        )
      );
      updates.add(update);
    });
    return updates;
  }

  public Log logDelete(Long userAccountId, String controllerClass, Long subjectId) {
    return logRepository.save (
      new Log(this.userRepository.findById(userAccountId), controllerClass, subjectId, "delete", null, null, null)
    );
  }

}