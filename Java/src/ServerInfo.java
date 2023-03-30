/**
 * ServerInfo
 * INFO RECEVIED FROM GETS:serverType serverID state curStartTime core memory
 * disk #waitingJobs
 * #runningJobs
 */

public class ServerInfo {

    private String type;
    private int id;
    private String state;
    private int curStartTime;
    private int core;
    private int memory;
    private int disk;
    private int waitingJobs;
    private int runningJobs;

    public ServerInfo(String[] data) {
        this.type = data[0];
        this.id = Integer.parseInt(data[1]);
        this.state = data[2];
        this.curStartTime = Integer.parseInt(data[3]);
        this.core = Integer.parseInt(data[4]);
        this.memory = Integer.parseInt(data[5]);
        this.disk = Integer.parseInt(data[6]);
        this.waitingJobs = Integer.parseInt(data[7]);
        this.runningJobs = Integer.parseInt(data[8]);
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCurStartTime() {
        return this.curStartTime;
    }

    public void setCurStartTime(int curStartTime) {
        this.curStartTime = curStartTime;
    }

    public int getCore() {
        return this.core;
    }

    public void setCore(int core) {
        this.core = core;
    }

    public int getMemory() {
        return this.memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getDisk() {
        return this.disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    public int getWaitingJobs() {
        return this.waitingJobs;
    }

    public void setWaitingJobs(int waitingJobs) {
        this.waitingJobs = waitingJobs;
    }

    public int getRunningJobs() {
        return this.runningJobs;
    }

    public void setRunningJobs(int runningJobs) {
        this.runningJobs = runningJobs;
    }

}