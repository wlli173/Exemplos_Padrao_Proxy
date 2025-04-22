package exemplo2.proxy;

import java.util.Date;

public class LogEntry {
    
    private Date timestamp;
    private String action;
    private String sql;
    
    public LogEntry(Date timestamp, String action, String sql) {
        this.timestamp = timestamp;
        this.action = action;
        this.sql = sql;
    }
    
    @Override
    public String toString() {
        return "LOG [" + timestamp + "]: " + action + " - " + sql;
    }
    
    // Getters
    public Date getTimestamp() { 
        return timestamp; 
    }
    public String getAction() { 
        return action; 
    }
    public String getSql() { 
        return sql; 
    }

}
