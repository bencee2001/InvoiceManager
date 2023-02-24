# InvoiceManager

Az applikáció elkészítéséhez használtam Spring Boot-ot, Spring Security-t. A frontend oldalhoz Thymeleaf-et használtam, az adatbázishoz pedig PostgreSQL-t.

A belépéshez 3 alap felhasználó van. Felhasználó neveik: “user”, “book”,”admin”. A jelszó minden felhasználónál 1234.

A role-ok megfelelnek az alap felhasználó neveknek: “USER”, “BOOK”(as Bookkeeper),”ADMIN”.

Követelményeknél a CAPTCHA-t nem sikerült megvalósítanom. A User osztályban a failedLoginAttemps ennek a próbálkozásából maradt, valamint ugyan így a MyLoginFailureHandler osztály is.

 
