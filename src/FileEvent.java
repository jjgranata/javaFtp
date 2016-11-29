import java.io.Serializable;

public class FileEvent implements Serializable {

    public FileEvent() {

    }

    private static final long serialVersionUID = 1L;

    private String destinationDirectory;
    private String sourceDirectory;
    private String filename;
    private long fileSize;
    private byte[] fileData;
    private String status;

    public String getDestFolder() {

        return destinationDirectory;
    }

    public void setDestFolder(String destFolder) {
        this.destinationDirectory = destFolder;
    }

    public String getSrcFolder()
    {
        return sourceDirectory;
    }

    public void setSrcFolder(String srcFolder) {

        this.sourceDirectory = srcFolder;
    }

    public String getFilename() {

        return filename;
    }

    public void setFilename(String name) {

        this.filename = name;
    }

    public long getSize()
    {
        return fileSize;
    }

    public void setSize(long size) {

        this.fileSize = size;
    }

    public String getState() {

        return status;
    }

    public void setState(String state) {

        this.status = state;
    }

    public byte[] getData() {

        return fileData;
    }

    public void setData(byte[] data) {

        this.fileData = data;
    }
}
