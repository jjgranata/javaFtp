package cs380;

import java.io.Serializable;

/**
 * File must be present on both the client and server ends; might need to put this in a package and call it.
 */

public class FileEvent implements Serializable {

    public FileEvent(long in) {
    	serialVersionUID = in;
    	System.out.println(serialVersionUID);
    }

    private static long serialVersionUID;

    private String destinationDirectory;
    private String sourceDirectory;
    private String filename;
    private long fileSize;
    public byte[] fileData;
    private String status;
    private String hash;
    private byte smallbyte;
    private byte[] newData;
    
    public byte getsmallbyte()
    {
    	return smallbyte;
    }
    
    public void setsmallbyte(byte in)
    {
    	smallbyte = in;
    }
    
    public byte[] getnewData()
    {
    	return newData;
    }
    
    public void setnewData(byte[] in){
    	newData = new byte[in.length];
    	for(int i = 0; i < in.length; i++)
    	{
    		newData[i] = in[i];
    	}
    }

    public String getHash()
    {
    	return hash;
    }
    
    public void setHash(String in){
    	this.hash = in;
    }
    
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
