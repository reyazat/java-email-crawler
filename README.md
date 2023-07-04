# java-email-crawler
## Email Crawler

This Java project is an email crawler that connects to an IMAP server, retrieves emails from the inbox folder, and extracts email addresses from the email content. It uses JavaMail API to establish the connection, retrieve emails, and parse the email content.

## Prerequisites

Before running the project, ensure that you have the following:

- Java Development Kit (JDK) installed on your system.
- JavaMail API library added to your project dependencies.

## Setup

1. Import the required Java libraries:
```java
import java.net.*;
import java.io.*;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.ReadOnlyFolderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.internet.InternetAddress;
import javax.mail.Address;
import javax.mail.*;
import java.util.*;
import java.util.regex.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;
import java.lang.InterruptedException;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
```

2. Set up the database connection by modifying the `url`, `user`, and `password` variables in the `JDBCTest` class:
```java
private static final String url = "jdbc:mysql://localhost:3306/databasename?useUnicode=true&characterEncoding=UTF-8";
private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
private static final String user = "user";
private static final String password = "password";
```

3. Implement the necessary methods for interacting with the database and handling email-related tasks in the `JDBCTest` class.

4. Create an instance of the `emailcrawler` class and call the `processMail` method to start retrieving and processing emails:
```java
emailcrawler crawler = new emailcrawler();
crawler.processMail(0);
```

## Usage

1. Compile the Java source file(s) using the Java compiler:
```
javac emailcrawler.java
```

2. Run the compiled Java class file:
```
java emailcrawler
```

The email crawler will connect to the specified IMAP server, retrieve emails from the inbox folder, extract email addresses, and store them in the database.
