// JOB INFORMATION:type submitTime id estimateRunTime core memory disk
public class Job {
    private String type;
    private int submitTime;
    private int id;
    private int estimateRunTime;
    private int requiredNumOfCores;
    private int requiredMemorySpace;
    private int requiredDiskSpace;

    public Job(String[] info) {
        this.type = info[0];
        this.submitTime = Integer.parseInt(info[1]);
        this.id = Integer.parseInt(info[2]);
        this.estimateRunTime = Integer.parseInt(info[3]);
        this.requiredNumOfCores = Integer.parseInt(info[4]);
        this.requiredMemorySpace = Integer.parseInt(info[5]);
        this.requiredDiskSpace = Integer.parseInt(info[6]);
    }

    public String getRequiredResources() {
        return getRequiredNumOfCores() + " " + getRequiredMemorySpace() + " " + getRequiredDiskSpace() + "\n";
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSubmitTime() {
        return this.submitTime;
    }

    public void setSubmitTime(int submitTime) {
        this.submitTime = submitTime;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstimateRunTime() {
        return this.estimateRunTime;
    }

    public void setEstimateRunTime(int estimateRunTime) {
        this.estimateRunTime = estimateRunTime;
    }

    public int getRequiredNumOfCores() {
        return this.requiredNumOfCores;
    }

    public void setRequiredNumOfCores(int requiredNumOfCores) {
        this.requiredNumOfCores = requiredNumOfCores;
    }

    public int getRequiredMemorySpace() {
        return this.requiredMemorySpace;
    }

    public void setRequiredMemorySpace(int requiredMemorySpace) {
        this.requiredMemorySpace = requiredMemorySpace;
    }

    public int getRequiredDiskSpace() {
        return this.requiredDiskSpace;
    }

    public void setRequiredDiskSpace(int requiredDiskSpace) {
        this.requiredDiskSpace = requiredDiskSpace;
    }

}
