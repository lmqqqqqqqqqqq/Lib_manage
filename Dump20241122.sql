-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: lib_schema
-- ------------------------------------------------------
-- Server version	8.0.39

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
  `genre` varchar(45) NOT NULL,
  `publisher` varchar(45) NOT NULL,
  `ISBN` varchar(45) NOT NULL,
  `language` varchar(45) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `rating` varchar(45) NOT NULL,
  PRIMARY KEY (`idbooks`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'The Night Circus','Erin Morgenstern','2024-10-28','/com/example/bookImage/NightCircus.jpg','Erin Morgenstern\'s novels transport you to magical destinations. Her follow-up to this, The Starless Sea, imagines an incredible subterranean world beneath New York City—but it\'s the magical, black-and-white Night Circus which will hold you in thrall through every page.','Romance','National Geographic Books','9780307744432','en (English)',0,'3.4'),(2,'On Earth We\'re Briefly Gorgeous','Ocean Vuong','2024-10-28','/com/example/bookImage/OnEarthWereBrieflyGorgeous.jpg','Ocean Vuong is a Vietnamese-American poet and his debut novel is written in the form of a letter from a son to a mother who cannot read. Exploring race, class, and masculinity, the novel handles difficult topics with beauty and the kind of lines that will hang in the air long after you\'ve set the book down.','Romance','The New York Times','9873612732167','vi (Vietnamese)',0,'3.2'),(7,'All About Love','Bell Hooks','2024-10-28','/com/example/bookImage/AllAboutLove.jpg','Feminist scholar and activist bell hooks died in 2021 at age 69, but her works have long been and will remain timeless. Her 1999 book is, as the title says, all about love, from personal, psychological, and philosophical perspectives.','Romance','Kindle Books','9780060959470','No language available',0,'3.3'),(9,'World Travel: An Irreverent Guide','Anthony Bourdain','2024-10-28','/com/example/bookImage/WorldTravel.jpg','Had to cancel your dream vacation due to the pandemic? This posthumous collection of essays and reflections captures the late travel and food writer and TV host Anthony Bourdain\'s favorite places on the planet—and may just inspire your future travels.','Non-fiction','The New York Times','9780062802798','en (English)',0,'3.0'),(10,'The Art of War','Sun Tzu','2024-10-01','/com/example/bookImage/TheArtOfWar.png','Ancient military strategy text','Philosophy','Ancient Wisdom','9781599869773','zh (Chinese)',0,'3.9'),(11,'1984','George Orwell','2024-09-15','/com/example/bookImage/1984.jpg','Dystopian novel on totalitarian society','Fiction','Secker & Warburg','9780451524935','en (English)',0,'4.2'),(12,'The Hobbit','J.R.R. Tolkien','2024-11-05','/com/example/bookImage/TheHobbit.jpg','Fantasy novel about a hobbit\'s adventure','Fantasy','George Allen & Unwin','9780345339683','en (English)',0,'4.3'),(13,'To Kill a Mockingbird','Harper Lee','2024-10-20','/com/example/bookImage/ToKillAMockingBird.jpg','Novel on racial injustice in the Deep South','Fiction','J.B. Lippincott & Co.','9780061120084','en (English)',0,'4.0'),(14,'The Catcher in the Rye','J.D. Salinger','2024-10-08','/com/example/bookImage/TheCatcherInTheRye.jpg','Novel about teenage alienation and angst','Fiction','Little, Brown and Company','9780316769488','en (English)',0,'3.8'),(15,'Pride and Prejudice','Jane Austen','2024-09-28','/com/example/bookImage/PrideAndPrejudice.jpg','Romantic novel on manners and marriage','Romance','T. Egerton','9780141439518','en (English)',0,'4.4'),(16,'Moby-Dick','Herman Melville','2024-08-10','/com/example/bookImage/MobyDick.jpg','Epic sea adventure novel','Adventure','Harper & Brothers','9781503280786','en (English)',0,'3.2'),(17,'Les Misérables','Victor Hugo','2024-07-14','/com/example/bookImage/LesMiserables.jpg','Historical novel about social justice','Historical','A. Lacroix','9780140444308','fr (French)',0,'4.7'),(18,'Crime and Punishment','Fyodor Dostoevsky','2024-06-22','/com/example/bookImage/CrimeAndPunishment.jpg','Psychological novel about crime and guilt','Philosophy','The Russian Messenger','9780486415871','ru (Russian)',0,'2.8'),(19,'Don Quixote','Miguel de Cervantes','2024-05-01','/com/example/bookImage/DonQuixote.jpg','Story of a man who becomes a knight','Fiction','Francisco de Robles','9780060934347','es (Spanish)',0,'4.5'),(20,'Animal Farm','George Orwell','2024-09-30','/com/example/bookImage/AnimalFarm.jpg','Political allegory on the Russian Revolution','Fiction','Secker & Warburg','9780451526342','en (English)',0,'2.9'),(21,'Homage to Catalonia','George Orwell','2024-09-29','/com/example/bookImage/HomageToCatalonia.jpg','Orwell\'s personal account of the Spanish Civil War','Non-fiction','Secker & Warburg','9780156421171','en (English)',0,'3.0'),(22,'Down and Out in Paris and London','George Orwell','2024-09-28','/com/example/bookImage/DownAndOutInParisAndLondon.jpg','Memoir on poverty in Paris and London','Non-fiction','Victor Gollancz Ltd','9780156262248','en (English)',0,'2.7'),(23,'The Fellowship of the Ring','J.R.R. Tolkien','2024-11-01','/com/example/bookImage/TheFellowshipOfTheRing.jpg','First book in The Lord of the Rings series','Fantasy','George Allen & Unwin','9780547928210','en (English)',0,'3.8'),(24,'The Two Towers','J.R.R. Tolkien','2024-11-02','/com/example/bookImage/TheTwoTowers.jpg','Second book in The Lord of the Rings series','Fantasy','George Allen & Unwin','9780547928203','en (English)',0,'3.4'),(25,'The Return of the King','J.R.R. Tolkien','2024-11-03','/com/example/bookImage/TheReturnOfTheKing.jpg','Final book in The Lord of the Rings series','Fantasy','George Allen & Unwin','9780547928197','en (English)',0,'4.0'),(26,'Emma','Jane Austen','2024-09-20','/com/example/bookImage/Emma.jpg','Novel about youthful hubris and romantic misunderstandings','Romance','John Murray','9780141439587','en (English)',0,'3.0'),(27,'Sense and Sensibility','Jane Austen','2024-09-18','/com/example/bookImage/SenseAndSensibility.jpg','Romantic novel about the Dashwood sisters','Romance','Thomas Egerton','9780141439662','en (English)',0,'3.3'),(28,'Mansfield Park','Jane Austen','2024-09-15','/com/example/bookImage/MansfieldPark.jpg','Novel about a young girl\'s journey to find her place in society','Romance','Thomas Egerton','9780141439808','en (English)',0,'2.8'),(29,'War and Peace','Leo Tolstoy','2024-08-01','/com/example/bookImage/WarAndPeace.jpg','Epic novel about the Napoleonic Wars','Historical','The Russian Messenger','9780199232765','ru (Russian)',0,'3.2'),(30,'Anna Karenina','Leo Tolstoy','2024-08-02','/com/example/bookImage/AnnaKarenina.jpg','Tragic novel about love and society','Romance','The Russian Messenger','9780199536061','ru (Russian)',0,'3.7'),(31,'The Death of Ivan Ilyich','Leo Tolstoy','2024-08-03','/com/example/bookImage/TheDeathOfIvanIlyich.jpg','Novella about existential reflection and mortality','Philosophy','The Russian Messenger','9780140445084','ru (Russian)',0,'2.2'),(32,'Harry Potter and the Prisoner of Azkaban','J.K. Rowling','2024-08-14','/com/example/bookImage/HarryPotterAndThePrisonerOfAzkaban.jpg','Harry Potter and the Prisoner of Azkaban is a fantasy novel written by the British author J. K. Rowling. It is the third instalment in the Harry Potter series. The novel follows Harry Potter, a young wizard, in his third year at Hogwarts School of Witchcraft and Wizardry. Along with friends Ron Weasley and Hermione Granger, Harry investigates Sirius Black, an escaped prisoner from Azkaban, the wizard prison, believed to be one of Lord Voldemort\'s old allies.','Fantasy','Bloomsbury Children’s Books','9780060935467','en (English)',0,'3.5'),(33,'Harry Potter and the Order of the Phoenix','J.K. Rowling','2024-07-01','/com/example/bookImage/HarryPotterAndTheOrderOfThePhoenix.jpg','It is the fifth novel in the Harry Potter series. It follows Harry Potter\'s struggles through his fifth year at Hogwarts School of Witchcraft and Wizardry, including the surreptitious return of the antagonist Lord Voldemort, O.W.L. exams, and an obstructive Ministry of Magic.','Fantasy','Bloomsbury Children’s Books','9780451524935','en (English)',0,'4.0'),(34,'The Great Gatsby','F. Scott Fitzgerald','2024-06-15','/com/example/bookImage/TheGreatGatsby.jpg','The Great Gatsby, novel by F. Scott Fitzgerald, published in 1925 by Charles Scribner’s Sons. Set in Jazz Age New York, it tells the story of Jay Gatsby, a self-made millionaire, and his pursuit of Daisy Buchanan, a wealthy young woman whom he loved in his youth.','Fiction','Charles Scribner\'s Sons','9780743273565','en (English)',0,'4.2'),(35,'Harry Potter and the Chamber of Secrets','J.K. Rowling','2024-05-10','/com/example/bookImage/HarryPotterAndTheChamberOfSecrets.jpg','Harry Potter and the Chamber of Secrets is a 1998 young adult fantasy novel by J.K. Rowling, the second in the Harry Potter series. The story follows Harry’s tumultuous second year at Hogwarts School of Witchcraft and Wizardry, including an encounter with Voldemort, the wizard who killed Harry’s parents.','Fantasy','Bloomsbury Children’s Books','9781503290563','en (English)',0,'4.7'),(36,'Harry Potter and the Half-Blood Price','J.K. Rowling','2024-04-20','/com/example/bookImage/HarryPotterAndTheHalfBloodPrince.jpg','\'Harry Potter and the Half-Blood Prince\' excels in character development, revealing deeper motives, humanizing villains, and introducing pivotal, emotionally charged plot twists.','Fantasy','Bloomsbury Children’s Books','9781503280786','en (English)',0,'3.8'),(37,'Brave New World','Aldous Huxley','2024-03-18','/com/example/bookImage/BraveNewWorld.jpg','A dystopian novel about a controlled, conformist society.','Fiction','Chatto & Windus','9780060850524','en (English)',0,'3.0'),(38,'The Ministry Of Time','Kaliane Bradley','2024-02-12','/com/example/bookImage/TheMinistryOfTime.jpg','A top-secret, time machine mission brings together an Antarctic explorer from the 19th century and a modern-day government agent—and sparks fly. If you\'re all-in on a time-travel romance (and don\'t want a tearjerker like The Time Traveler\'s Wife), this is your must-read.','Romance','Little, Brown and Company','9780316769488','en (English)',0,'4.2'),(39,'The Grapes of Wrath','John Steinbeck','2024-01-30','/com/example/bookImage/TheGrapesOfWrath.jpg','A novel about the struggles of the Great Depression era.','Fiction','The Viking Press','9780143039433','en (English)',0,'3.9'),(40,'One Hundred Years of Solitude','Gabriel Garcia Marquez','2024-01-15','/com/example/bookImage/OneHundredYearsSolitude.jpg','A multi-generational story of the Buendia family.','Fiction','Harper & Row','9780060883287','es (Spanish)',0,'2.9'),(41,'Demon Copperhead','Barbara Kingsolver','2023-12-05','No image available','Reboots aren\'t always successful, but this masterfull retelling of Dickens\' David Copperfield resets the story in modern-day Appalachia. It\'s a Pulitzer Prize winner, and a book you won\'t want to put down.','Fiction','The Russian Messenger','9780140449136','ru (Russian)',0,'3.5'),(42,'Crying in H Mart','Michelle Zauner','2024-05-10','No image available','This is the memoir and debut book of Michelle Zauner, lead singer of the indie band Japanese Breakfast. Zauner writes about growing up Korean American and how losing her mother to cancer when she was 25 forced her to reconnect with her identity. It\'s a story of Zauner\'s grief and an exploration of all the gifts (language, food, history) her mother left behind. Zauner\'s memoir would be a wonderful addition to the growing list of great mother-daughter books.','Philosophy','Secker & Warburg','9780763463287','No language available',0,'4.0'),(43,'Harry Potter and the Philosopher\'s Stone','J.K. Rowling','2024-11-21','/com/example/bookImage/HarryPotterAndThePhilosopherStone.jpg','The fantasy novel is set in the 1990s in Great Britain as a third-person omniscient narrator guides the reader into the magical wizarding world. The novel contains friendship, bravery, good vs. evil, and family themes. Harry Potter and the Sorcerer’s Stone was made into a film of the same name and is the third best-selling novel of all time.','Fantasy','Bloomsbury Children’s Books','9780590353427','en (English)',0,'4.8');
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
  `is_favourite` tinyint(1) DEFAULT '0',
  `in_progress` tinyint(1) DEFAULT '0',
  `borrow` tinyint(1) DEFAULT '0',
  `is_watched` tinyint(1) DEFAULT '1',
  `borrow_date` date DEFAULT NULL,
  `due_date` date DEFAULT NULL,
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
INSERT INTO `user_books` VALUES (3,32,NULL,0,0,0,1,NULL,NULL),(3,33,NULL,0,0,0,1,NULL,NULL),(5,32,NULL,1,0,1,1,'2024-11-22','2024-12-02');
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
  `staff` tinyint DEFAULT '0',
  PRIMARY KEY (`idusers`),
  UNIQUE KEY `idusers_UNIQUE` (`idusers`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'buimanhnamlcvn','1234567','Nam','Bui','20','4','2005','zxc',NULL,NULL,NULL,0),(2,'buimanhnam','a1234567','dwadw','wdwd','2','2','1901','sad','file:/C:/Users/elect/OneDrive/Desktop/anh/z4310289437950_ac97519d36c2b001bcbbe543dca940f9.jpg','2024-11-11',1,0),(3,'zxczxc','zxczxc11','le','quan','3','9','2005','x','/com/example/image/user.jpg','2024-11-19',1,0),(4,'tuannghia','Pro9x224','Nghĩa','Tuấn','11','8','2005','1234567','/com/example/image/user.jpg','2024-11-21',0,0),(5,'zxczxczxc','zxczxc11','quan','le','3','9','2005','z','/com/example/image/user.jpg','2024-11-22',1,0);
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

-- Dump completed on 2024-11-22  9:11:09
