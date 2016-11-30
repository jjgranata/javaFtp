package cs380;

import java.io.Serializable;

/**
 * File must be present on both the client and server ends; might need to put this in a package and call it.
 */

public class FileEvent implements Serializable {

    public FileEvent(byte[] arr) {
    	fileData = arr;
    	for(int i = 0; i < 5; i++){
    		System.out.print(fileData[i] + "[]");
    	}
    }

    private static final long serialVersionUID = 1L;

    private String destinationDirectory;
    private String sourceDirectory;
    private String filename;
    private long fileSize;
    public byte[] fileData;
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
