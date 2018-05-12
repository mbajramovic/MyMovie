Bajramovic Maid
17411
2016/17

Spirala 1

Uradeno:
- kreirana pocetna aktivnost, tako da ekran sadrži tri dugmeta sa listom glumaca, pri cemu se pojedinacni glumac prikazuje na specificirani nacin
- kreirana aktivnost biografija_glumca_v2 koja sadrži biografiju pojedinog glumca
- povezana aktivnost biografija_glumca_v2 sa pojedinacnim glumcem
- kreirana još dvije dodatne aktivnosti: režiseri i žanrovi, pri cemu režiseri imaju samo ime i prezime, a žanrovi naziv i ikonicu
- povezane odgovarajuce aktivnosti sa odgovarajucim dugmadima
- omogucen prikaz IMBd stranice glumca, nakon klika na link
- dodano dugme PODIJELI koja omogucava proslijedivanje teksta biografije nekoj od vec instaliranih aplikacija

Nije uradeno:
/

Spirala 2


Uradeno:
- layout pocetne aktivnosti je modificiran tako da sadrzi dva FrameLayout-a
- kreiran je FragmentDugmadi (za "obicni" ekran, ako i za ekrane >= 500dp), sa tri button-a i jednom ikonicom
- kreirani su i sljedeci fragmenti: FragmentGlumaca, FragmentRezisera, FragmentZanrova, FragmentDetalja
- u zavisnosti od klika mijenjaju se fragmenti
- klikom na back vraca se prethodni fragment

- kreiran je novi layout i za pocetnu aktivnost ako je sirina ekrana >= 500dp
- omogucen je prikaz glumaca i detalja o glumcu, te rezisera i zanrova, u zavisnosti od klika
- zbog neurednosti prikaza kada je sirina ekrana >= 500dp, kreiran je i layout detalja o glumcu za sirinu >= 500dp, gdje je smanjena slika glumca, smanjena slova i uklonjena informacija o mjestu rodenja glumca (koja inace pise u LISTI glumaca)

- sve koristene labele su prevedene na engleski
- kako bi se i informacija o spolu prilagodila jeziku, kreirani su i stringovi "muskiSpol" i "zenskiSpol"
- pored pojedinih stringova je dodana i dvotacka (ne znam koliko je to ok, ali nisam znao na drugi nacin napisati npr. Godina rodjenja:)
- u zavisnosti od jezika mijenja se ikonica

Nije uradeno:
/

Spirala 3

Uradeno:

- omogucena pretraga glumaca po imenu, koristeci themoviedb web api
- koristeci ID glumca dohvataju se detalji glumca, te se klikom na glumca prikazuju u fragmentu
- omogucen je pregled režisera sa kojim je glumac radio na zadnjih 7 filmova (bez duplikata)
- omogucen je pregled žanrova zadnjih 7 filmova u kojima je glumac glumio (bez duplikata), pri
cemu je slika stavljena za samo neke od žanrova
- korištenjem biblioteke Picasso prikazuje se i slika glumca (u listi glumaca, kao i u detaljima glumca)
- prilikom rotiranja mobitela lista zanrova i rezisera se popuni sa zanrovima i reziserima vezanim za 
prvog glumca u listi (smatra se kao da je vec kliknut taj glumac, obzirom da se na na desnom fragmentu
prikazuje njega biografija)
- prilikom svake nove pretrage (tj. klika na dugme Pretrazi/Search) lista rezisera i zanrova se "prazne"

Nije uradeno:
/

Spirala 4

Uradeno:
- kreirane su tri tabele - Glumci, Reziseri i Zanrovi, koje su trenutno prazn
- klikom na dugme Bookmark, glumac se sprema u bazu, te njemu pripadajuci režiseri i žanrovi, bez duplikata
- unosom actor:Ime omogucena je pretraga glumaca iz baze, te pregled detalja o glumcu, žanrova i režisera
- unosom director:Ime omogucena je pretraga glumaca koji su radili sa unesenim reziserom
- omogucena je opcija REMOVE BOOKMARK koja briše glumca iz baze, i po potrebi, njegove žanrove i režisere
- dodana su dva fragmenta - za pretragu filmova i za unos filma u kalendar
- klikom na dugme zapamti, odabrani film se sprema u kalendar, korištenjem CalendarProvider-a
- ispravljane su uocene greske sa prosle spirale (koje su uzrokovale krahiranje)
- dodani fragmenti su prilagodeni i širokom layout-u

Nije uradeno:
/
