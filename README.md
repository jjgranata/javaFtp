# javaFtp

Features - Sender	

 	· Sender must authenticate with receiver via user name and password (user names and passwords can be stored on receiver in the clear, but not hard coded). EC if salted hashes are used.	
	· Program will need to read a file to send chunks, An amount of data bytes, at a time. You may decide the size of the chunks, within 1Kb-1MB.	
	· The chunk is then check-summed/hashed for integrity.	
	· Then, the chunk and associated hash are encrypted using simple XOR cipher.	
	· If ASCII armoring is requested, data is MIME Base64 encoded prior to transmission	
	· If notified by receiver of a failed transmission, try again up to N times (at least 3).	
	· If notified that receiver is terminating connection, sender must do so as well.
  
Features - Receiver

	· Sender must authenticate via user name and password stored on the receiver. Plain text storage is acceptable, but extra points are possible if Salted/Hashed passwords (Method of storing passwords to prevent easy cracking/guessing) are used.
	· Upon data receipt, receiver is to (optionally) Base64 decode data, decrypt, and then verify the hash.
	· If failure, notify sender to retry. Support at least 3 retry attempts (you may support more). After N failed attempts, notify sender and disconnect.
	· Upon successful receipt, save the data chunk.
	· Notify the sender of a successful completion of file transfer (if it did).
	· Notify the sender the receiver is terminating connection.
	· Terminate connection
  
Encryption / Decryption

	Encryption/Decryption will use a basic XOR cipher using a key provided by the instructor at demonstration time.
	The key will be a file of size ranging anywhere from 10 bytes to 10 Petabytes, containing random bytes.
	The key will be provided for both sender and receiver via some means (thumbdrive, file copy, web site, etc.).
