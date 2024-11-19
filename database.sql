-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: beta_data
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `idbooks` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `author` varchar(100) NOT NULL,
  `created_date` date NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `description` text,
  `subject` varchar(45) NOT NULL,
  `publisher` varchar(45) NOT NULL,
  `ISBN` varchar(45) NOT NULL,
  `language` varchar(45) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idbooks`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'bruh bruh','bombaclat','2024-10-28',NULL,'troolllllolll','','','','',0),(2,'lmo lmo','fiiz','2024-10-28',NULL,'lmao','','','','',0),(7,'lmoabgbfgbabgfbfgbgbaa lmo','fiqbgfngfnweiz','2024-10-28',NULL,'lmvdvdvderewrfvdvao','','','','',0),(9,'something','fizz','2024-10-28',NULL,'lmvdvd','fiction','minecraft','33212134','english',0),(10,'The Art of War','Sun Tzu','2024-10-01',NULL,'Ancient military strategy text','Philosophy','Ancient Wisdom','9781599869773','Chinese',0),(11,'1984','George Orwell','2024-09-15',NULL,'Dystopian novel on totalitarian society','Fiction','Secker & Warburg','9780451524935','English',0),(12,'The Hobbit','J.R.R. Tolkien','2024-11-05',NULL,'Fantasy novel about a hobbit\'s adventure','Fantasy','George Allen & Unwin','9780345339683','English',0),(13,'To Kill a Mockingbird','Harper Lee','2024-10-20',NULL,'Novel on racial injustice in the Deep South','Fiction','J.B. Lippincott & Co.','9780061120084','English',0),(14,'The Catcher in the Rye','J.D. Salinger','2024-10-08',NULL,'Novel about teenage alienation and angst','Fiction','Little, Brown and Company','9780316769488','English',0),(15,'Pride and Prejudice','Jane Austen','2024-09-28',NULL,'Romantic novel on manners and marriage','Romance','T. Egerton','9780141439518','English',0),(16,'Moby-Dick','Herman Melville','2024-08-10',NULL,'Epic sea adventure novel','Adventure','Harper & Brothers','9781503280786','English',0),(17,'Les Misérables','Victor Hugo','2024-07-14',NULL,'Historical novel about social justice','Historical','A. Lacroix','9780140444308','French',0),(18,'Crime and Punishment','Fyodor Dostoevsky','2024-06-22',NULL,'Psychological novel about crime and guilt','Philosophy','The Russian Messenger','9780486415871','Russian',0),(19,'Don Quixote','Miguel de Cervantes','2024-05-01',NULL,'Story of a man who becomes a knight','Fiction','Francisco de Robles','9780060934347','Spanish',0),(20,'Animal Farm','George Orwell','2024-09-30',NULL,'Political allegory on the Russian Revolution','Fiction','Secker & Warburg','9780451526342','English',0),(21,'Homage to Catalonia','George Orwell','2024-09-29',NULL,'Orwell\'s personal account of the Spanish Civil War','Non-fiction','Secker & Warburg','9780156421171','English',0),(22,'Down and Out in Paris and London','George Orwell','2024-09-28',NULL,'Memoir on poverty in Paris and London','Non-fiction','Victor Gollancz Ltd','9780156262248','English',0),(23,'The Fellowship of the Ring','J.R.R. Tolkien','2024-11-01',NULL,'First book in The Lord of the Rings series','Fantasy','George Allen & Unwin','9780547928210','English',0),(24,'The Two Towers','J.R.R. Tolkien','2024-11-02',NULL,'Second book in The Lord of the Rings series','Fantasy','George Allen & Unwin','9780547928203','English',0),(25,'The Return of the King','J.R.R. Tolkien','2024-11-03',NULL,'Final book in The Lord of the Rings series','Fantasy','George Allen & Unwin','9780547928197','English',0),(26,'Emma','Jane Austen','2024-09-20',NULL,'Novel about youthful hubris and romantic misunderstandings','Romance','John Murray','9780141439587','English',0),(27,'Sense and Sensibility','Jane Austen','2024-09-18',NULL,'Romantic novel about the Dashwood sisters','Romance','Thomas Egerton','9780141439662','English',0),(28,'Mansfield Park','Jane Austen','2024-09-15',NULL,'Novel about a young girl\'s journey to find her place in society','Romance','Thomas Egerton','9780141439808','English',0),(29,'War and Peace','Leo Tolstoy','2024-08-01',NULL,'Epic novel about the Napoleonic Wars','Historical','The Russian Messenger','9780199232765','Russian',0),(30,'Anna Karenina','Leo Tolstoy','2024-08-02',NULL,'Tragic novel about love and society','Romance','The Russian Messenger','9780199536061','Russian',0),(31,'The Death of Ivan Ilyich','Leo Tolstoy','2024-08-03',NULL,'Novella about existential reflection and mortality','Philosophy','The Russian Messenger','9780140445084','Russian',0),(32,'To Kill a Mockingbird','Harper Lee','2024-08-14',NULL,'A novel about racial injustice in the Deep South.','Fiction','J.B. Lippincott & Co.','9780060935467','English',0),(33,'1984','George Orwell','2024-07-01',NULL,'Dystopian novel about totalitarianism and surveillance.','Fiction','Secker & Warburg','9780451524935','English',0),(34,'The Great Gatsby','F. Scott Fitzgerald','2024-06-15',NULL,'A story of wealth and corruption in 1920s America.','Fiction','Charles Scribner\'s Sons','9780743273565','English',0),(35,'Pride and Prejudice','Jane Austen','2024-05-10',NULL,'Romantic novel about manners and marriage in early 19th century England.','Fiction','Thomas Egerton','9781503290563','English',0),(36,'Moby-Dick','Herman Melville','2024-04-20',NULL,'An epic tale of the pursuit of the white whale.','Fiction','Harper & Brothers','9781503280786','English',0),(37,'Brave New World','Aldous Huxley','2024-03-18',NULL,'A dystopian novel about a controlled, conformist society.','Fiction','Chatto & Windus','9780060850524','English',0),(38,'The Catcher in the Rye','J.D. Salinger','2024-02-12',NULL,'A story of teenage angst and alienation in 1950s America.','Fiction','Little, Brown and Company','9780316769488','English',0),(39,'The Grapes of Wrath','John Steinbeck','2024-01-30',NULL,'A novel about the struggles of the Great Depression era.','Fiction','The Viking Press','9780143039433','English',0),(40,'One Hundred Years of Solitude','Gabriel Garcia Marquez','2024-01-15',NULL,'A multi-generational story of the Buendia family.','Fiction','Harper & Row','9780060883287','Spanish',0),(41,'Crime and Punishment','Fyodor Dostoevsky','2023-12-05',NULL,'A psychological novel exploring guilt and redemption.','Fiction','The Russian Messenger','9780140449136','Russian',0),(42,'sad','asd','2024-05-10',NULL,'cscs','sa','sad','zxc','zxc',NULL);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_books`
--

DROP TABLE IF EXISTS `user_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_books` (
  `idusers` int NOT NULL,
  `idbooks` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `is_favorite` tinyint(1) DEFAULT '0',
  `in_progress` tinyint(1) DEFAULT '0',
  `borrow` tinyint(1) DEFAULT '0',
  `check` tinyint(1) DEFAULT '1',
  `borrow_date` datetime DEFAULT NULL,
  `due_date` datetime DEFAULT NULL,
  PRIMARY KEY (`idusers`,`idbooks`),
  KEY `idbooks` (`idbooks`),
  CONSTRAINT `user_books_ibfk_1` FOREIGN KEY (`idusers`) REFERENCES `users` (`idusers`) ON DELETE CASCADE,
  CONSTRAINT `user_books_ibfk_2` FOREIGN KEY (`idbooks`) REFERENCES `books` (`idbooks`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_books`
--

LOCK TABLES `user_books` WRITE;
/*!40000 ALTER TABLE `user_books` DISABLE KEYS */;
INSERT INTO `user_books` VALUES (1,1,'reading',0,1,0,1,NULL,NULL),(2,17,NULL,1,1,1,1,NULL,NULL),(2,20,NULL,1,1,1,1,NULL,NULL),(2,21,NULL,1,1,1,1,NULL,NULL),(2,22,NULL,1,1,1,1,NULL,NULL),(2,23,NULL,1,1,1,1,NULL,NULL),(2,24,NULL,1,1,1,1,NULL,NULL),(2,25,NULL,1,1,1,1,NULL,NULL),(2,26,NULL,1,1,1,1,NULL,NULL),(2,27,NULL,1,1,1,1,NULL,NULL),(2,28,NULL,1,1,1,1,NULL,NULL),(2,29,NULL,1,1,1,1,NULL,NULL),(2,30,NULL,1,1,1,1,NULL,NULL);
/*!40000 ALTER TABLE `user_books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `idusers` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `dayOfBirth` varchar(45) NOT NULL,
  `monthOfBirth` varchar(45) NOT NULL,
  `yearOfBirth` varchar(45) NOT NULL,
  `recoveryCode` varchar(45) NOT NULL,
  `avatar` varchar(225) DEFAULT NULL,
  `currentDate` varchar(45) DEFAULT NULL,
  `isSave` int DEFAULT NULL,
  PRIMARY KEY (`idusers`),
  UNIQUE KEY `idusers_UNIQUE` (`idusers`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'buimanhnamlcvn','1234567','Nam','Bui','20','4','2005','zxc',NULL,NULL,NULL),(2,'buimanhnam','a1234567','dwadw','wdwd','2','2','1901','sad','file:/C:/Users/elect/OneDrive/Desktop/anh/z4310289437950_ac97519d36c2b001bcbbe543dca940f9.jpg','2024-11-11',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-19 22:29:27
