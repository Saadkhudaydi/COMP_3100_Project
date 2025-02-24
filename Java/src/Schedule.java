import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Schedule
 * REQUIRMENTS FOR SCHEDULING: SCHD jobID serverType serverID
 */

public class Schedule {
    private static Map<String, Integer> scheduledServersMap = new LinkedHashMap<>(); // creating hashmap for the largest
    // server type
    private Job job;
    private ServerInfo server;
    private static int lastServerId;

    public Schedule(Job job, List<ServerInfo> servers) {
        this.job = job;
        this.server = servers.get(getFirstServer(servers));
    }

    private int getLRR(List<ServerInfo> servers) {
        String serverType = servers.get(0).getType();
        Schedule.scheduledServersMap.put(serverType, lastServerId);
        lastServerId = Schedule.scheduledServersMap.get(serverType);
        return (lastServerId++) % servers.size(); // adding a mod to not surpass the server size

    }

    private int getFirstServer(List<ServerInfo> servers) {
        int smallestIndex = 0;
        int largestId = 100;
        for (int i = 0; i < servers.size(); i++) {
            int serverId = servers.get(i).getId();
            if (largestId > serverId) {
                largestId = serverId;
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    public String scheduleJob(String SCHD) {
        return SCHD + job.getId() + " " + server.getType() + " " + server.getId() + "\n";
    }
}
