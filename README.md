# PROGI_23_24-Projekt_G21_i_G22_Grupa7
Repozitorij za projektni zadatak digitalizacija na predmetu Programsko Inženjerstvo na Fakultetu elektrotehnike i računarstva

Link na aplikaciju deployanu na servisu Render se nalazi ovdje:
https://kompletici-front.onrender.com

**Aplikacija za OCR (Optičko prepoznavanje znakova)**

**Opis projekta**

Ova aplikacija ima za cilj omogućiti korisnicima da jednostavno i brzo izvode optičko prepoznavanje znakova (OCR) na slikama koje uploadaju. Nakon što korisnik uploada sliku, aplikacija će obraditi sliku i vratiti mu tekst koji je isčitan iz slike.

**Uloge korisnika**

Aplikacija uključuje više razina korisničkih uloga, svaka s različitim privilegijama pregleda i manipulacije podacima. Evo popisa uloga:

_Direktor:_
Pravo pregleda statistike aktivnosti za sve korisnike.
Pregled cijelog povijesti.
Brisanje računa zaposlenika.
Potpisivanje skeniranih dokumenata.
_Revizor:_
Pregledavanje dokumenata koje su zaposlenici skenirali.
Promjena kategorije dokumenata.
Proslijeđivanje određenih dokumenata računovođi.
_Računovođa:_
Slanje dokumenata direktoru na potpisivanje.
Arhiviranje dokumenata.
_Zaposlenik:_
Skeniranje dokumenata.
Slanje skeniranih dokumenata revizoru.
Pregled vlastite povijesti skeniranja.
Autentikacija i Registracija

**Aplikacija počinje s Login i Register stranicama:**

Korisnici s postojećim računom mogu se prijaviti putem Login stranice.
Korisnici bez računa mogu izraditi novi račun putem Registracije.
Lokalno pokretanje aplikacije

**Aplikaciju je moguće lokalno pokrenuti na sljedeći način:**

Pokretanje baze u PGAdmin:
Inicijalizirajte bazu podataka u PGAdmin prema postavkama u priloženom SQL dumpu.
Pokretanje backenda u IntelliJ:
Uvezite backend projekt u IntelliJ.
Konfigurirajte postavke baze podataka u aplikaciji prema lokalnim postavkama.
Pokrenite backend aplikaciju.
Pokretanje frontenda u Reactu:
Uvezite frontend projekt u odgovarajući razvojni okoliš.
Otvorite terminal i navigirajte do direktorija frontend projekta.
Pokrenite frontend aplikaciju pomoću naredbe npm start.
Nakon ovih koraka, aplikacija će biti dostupna lokalno, a korisnici mogu pristupiti funkcionalnostima OCR-a, upravljati dokumentima te pregledavati i obrađivati podatke sukladno svojim ulogama.
